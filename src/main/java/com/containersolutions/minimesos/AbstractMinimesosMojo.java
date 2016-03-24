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
        mesosCluster.start();
    }

    protected void destroyMinimesos() {
        getMinimesosCluster().ifPresent(MesosCluster::stop);
        mesosClusterState.delete(getPluginContext());
    }

    protected Optional<MesosCluster> getMinimesosCluster() {
        return mesosClusterState.get(getPluginContext());
    }
}
