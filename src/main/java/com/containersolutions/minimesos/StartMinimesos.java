package com.containersolutions.minimesos;

import com.containersol.minimesos.MinimesosException;
import com.containersol.minimesos.cluster.MesosCluster;
import com.containersol.minimesos.config.ClusterConfig;
import com.containersol.minimesos.config.ConfigParser;
import com.containersol.minimesos.mesos.ClusterArchitecture;
import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;

/**
 * Start the minimesos cluster
 */
@Mojo( name = "start", defaultPhase = LifecyclePhase.PRE_INTEGRATION_TEST)
public class StartMinimesos extends AbstractMinimesosMojo
{
    /**
     * A relative or absolute path to a minimesos configuration file (a.k.a minimesosFile)
     */
    @Parameter(property = "configFile")
    private File configFile;

    @Parameter(defaultValue = "${project}", required = true, readonly = false)
    MavenProject project;

    public void execute() throws MojoExecutionException
    {
        ClusterArchitecture.Builder configBuilder;
        if (configFile == null) {
            configBuilder = new ClusterArchitecture.Builder();
            configBuilder.withZooKeeper();
            configBuilder.withMaster();
            configBuilder.withAgent();
        } else {
            configBuilder = parseConfigFile();
        }

        startMinimesos(configBuilder.build());

        MesosCluster mesosCluster = getMinimesosCluster().orElseThrow(() -> new IllegalStateException("Cannot obtain reference to minimesos cluster."));
        writeProperties(mesosCluster);
    }

    private void writeProperties(MesosCluster mesosCluster) {
        project.getProperties().setProperty("zookeeper_ip", mesosCluster.getZkContainer().getIpAddress());
        project.getProperties().setProperty("mesos_master_ip", mesosCluster.getMasterContainer().getIpAddress());
    }

    private ClusterArchitecture.Builder parseConfigFile() {
        try {
            ClusterConfig clusterConfig = new ConfigParser().parse(FileUtils.readFileToString(configFile));
            return ClusterArchitecture.Builder.createCluster(clusterConfig);
        } catch (Exception e) {
            String msg = String.format("Failed to load cluster configuration from %s: %s", configFile.getPath(), e.getMessage());
            throw new MinimesosException(msg, e);
        }
    }
}