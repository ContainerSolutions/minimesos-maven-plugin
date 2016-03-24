package com.containersolutions.minimesos;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;

/**
 * Says "Hi" to the user.
 *
 */
@Mojo( name = "stop", defaultPhase = LifecyclePhase.POST_INTEGRATION_TEST)
public class StopMinimesos extends AbstractMinimesosMojo
{
    public void execute() throws MojoExecutionException
    {
        destroyMinimesos();
    }
}