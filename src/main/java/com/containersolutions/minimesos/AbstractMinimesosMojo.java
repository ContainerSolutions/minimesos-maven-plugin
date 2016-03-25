package com.containersolutions.minimesos;

import com.containersol.minimesos.cluster.MesosCluster;
import com.containersol.minimesos.mesos.ClusterArchitecture;
import org.apache.maven.plugin.AbstractMojo;

import java.util.Optional;

public abstract class AbstractMinimesosMojo extends AbstractMojo {
    public static final String MESOS_CLUSTER_KEY = "mesos_cluster";
    private final MinimesosState<MesosCluster> mesosClusterState;

    public AbstractMinimesosMojo() {
        mesosClusterState = new MinimesosState<>(MESOS_CLUSTER_KEY);
    }

    protected void startMinimesos(ClusterArchitecture architecture) {
        destroyMinimesos(); // Kill previous minimesos if running
        MesosCluster mesosCluster = new MesosCluster(architecture);
        mesosClusterState.put(getPluginContext(), mesosCluster);

        getLog().info("Starting minimesos");
        mesosCluster.start();
        getLog().info("Started minimesos");
    }

    protected void destroyMinimesos() {
        mesosClusterState.delete(getPluginContext());   // Always delete, no matter what
        getMinimesosCluster().ifPresent(mesosCluster -> {
            getLog().info("Stopping minimesos");
            mesosCluster.stop();
        });
    }

    protected Optional<MesosCluster> getMinimesosCluster() {
        return mesosClusterState.get(getPluginContext());
    }
}
