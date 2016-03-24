# minimesos-maven-plugin
A Maven plugin for minimesos. Control your minimesos cluster from your pom.

# PRE RELEASE
Note this has not been released yet. The artifact does not exist on maven central.

# Quick start
Add the following plugin to your pom:

```
<plugin>
    <groupId>com.containersolutions.minimesos</groupId>
    <artifactId>minimesos-maven-plugin</artifactId>
    <version>0.1.0-SNAPSHOT</version>
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
```
# Usage
## Goals
Goal    | Description
---     | ---
`start` | Start the minimesos cluster
`stop`  | Stop the minimesos cluster