package com.containersolutions.minimesos;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

/**
 * Stop the minimesos cluster
 */
@Mojo( name = "stop", defaultPhase = LifecyclePhase.POST_INTEGRATION_TEST)
public class StopMinimesos extends AbstractMinimesosMojo
{
    public void execute() throws MojoExecutionException
    {
        destroyMinimesos();
    }
}