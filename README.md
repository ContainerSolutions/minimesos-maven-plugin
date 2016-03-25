# minimesos-maven-plugin
A Maven plugin for minimesos. Control your minimesos cluster from your pom.

# PRE RELEASE
Note this has not been released yet. The artifact does not exist on maven central.

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

It is recommended that you also specify the version of the plugin.

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
export VERSION=${THE_VERSION}
git checkout master
git pull
git checkout -b release/$VERSION
mvn versions:set -DnewVersion=$VERSION
git tag $VERSION
git push --set-upstream origin release/$VERSION
```