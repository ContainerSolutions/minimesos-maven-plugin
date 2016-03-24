package com.containersolutions.minimesos;

import com.containersol.minimesos.cluster.MesosCluster;
import com.containersol.minimesos.mesos.ClusterArchitecture;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

/**
 * Says "Hi" to the user.
 *
 */
@Mojo( name = "start", defaultPhase = LifecyclePhase.PRE_INTEGRATION_TEST)
public class StartMinimesos extends AbstractMinimesosMojo
{
    @Parameter(defaultValue = "${project}", required = true, readonly = false)
    MavenProject project;

    public void execute() throws MojoExecutionException
    {
        ClusterArchitecture.Builder builder = new ClusterArchitecture.Builder();
        builder.withZooKeeper();
        builder.withMaster();
        builder.withAgent();
        startMinimesos(builder.build());

        MesosCluster mesosCluster = getMinimesosCluster().orElseThrow(() -> new IllegalStateException("Cannot obtain reference to minimesos cluster."));
        project.getProperties().setProperty("zookeeper_ip_port", mesosCluster.getZkContainer().getFormattedZKAddress().replace("zk://", ""));
        project.getProperties().setProperty("mesos_master_ip", mesosCluster.getMasterContainer().getIpAddress());
    }
}