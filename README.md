# minimesos-maven-plugin
A Maven plugin for minimesos. Control your minimesos cluster from your pom.

# Quick start
Add the following plugin to your pom:

```
<pluginRepositories>
    <pluginRepository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </pluginRepository>
</pluginRepositories>

<build>
    <plugins>
        <plugin>
        <groupId>com.github.ContainerSolutions</groupId>
        <artifactId>minimesos-maven-plugin</artifactId>
            <executions>
                <execution>
                    <id>start</id>
                    <phase>pre-integration-test</phase>
                    <goals>
                        <goal>start</goal>
                    </goals>
                </execution>
                <execution>
                    <id>stop</id>
                    <phase>post-integration-test</phase>
                    <goals>
                        <goal>stop</goal>
                    </goals>
                </execution>
            </executions>
        </plugin>
        ...
```

It is recommended that you also specify the version of the plugin. Note that only tagged versions of the plugin will work when downloaded from jitpack. This is because Maven plugins require the version number specified in your project and the version number inside the plugin to match exactly.

## OSX (or VM) users
For people using a VM to host the docker daemon, ensure you setup a route to route traffic to the containers. Without this your cluster will appear to "doesn't start". Use the following command:
```
$ sudo route delete 172.17.0.0/16; sudo route -n add 172.17.0.0/16 $(docker-machine ip ${DOCKER_MACHINE_NAME})
```

# Usage
## Goals
Goal    | Description
---     | ---
`start` | Start the minimesos cluster
`stop`  | Stop the minimesos cluster

## Configuration
Setting         | Description
---             | ---
`configFile`    | A relative or absolute path to a minimesos configuration file (a.k.a minimesosFile)

For example:
```
<plugin>
    <groupId>com.containersolutions.minimesos</groupId>
    <artifactId>minimesos-maven-plugin</artifactId>
    <configuration>
        <configFile>./src/test/resources/minimesosFile</configFile>
    </configuration>
    ...
```

Settings may also be passed as Maven arguments. E.g. `mvn verify -DconfigFile=./myConfig`

## Exported maven properties
Property            | Description
---                 | ---
`zookeeper_ip`      | The IP address of the ZooKeeper container
`mesos_master_ip`   | The IP address of the Mesos master container

### Using exported maven properties
Use the properties to import into other variables. For example, when testing a Spring application, you will want to inject the maven properties into System properties:

```
<plugin>
    <artifactId>maven-failsafe-plugin</artifactId>
    <configuration>
        <systemProperties>
            <mesos.zookeeper.server>${zookeeper_ip}:2181</mesos.zookeeper.server>
            <mesos.master>${mesos_master_ip}:5050</mesos.master>
        </systemProperties>
    ...
```

# Releasing
To release:
```
git checkout master
git fetch origin
git reset --hard origin/master
git checkout -b release/$VERSION
mvn versions:set -DnewVersion=$VERSION
git add pom.xml
git commit -m "Set version number for release of $VERSION"
git tag $VERSION
git push --set-upstream origin release/$VERSION && git push origin $VERSION
```
Do not create a GH release. Travis will do this for you.
