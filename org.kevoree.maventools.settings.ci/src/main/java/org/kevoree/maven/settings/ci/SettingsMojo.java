package org.kevoree.maven.settings.ci;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by duke on 13/12/2013.
 */
@Mojo(name = "set", requiresProject = false, defaultPhase = LifecyclePhase.VALIDATE, threadSafe = true, requiresDirectInvocation = false)
public class SettingsMojo extends AbstractMojo {

    @Parameter(readonly = false, required = true, property = "project")
    private MavenProject project;

    @Parameter(readonly = true, required = false, property = "env.deployUsername")
    private String deployUsername;

    @Parameter(readonly = true, required = false, property = "env.deployPassword")
    private String deployPassword;

    @Parameter(readonly = true, required = false, property = "env.deployServerId")
    private String deployServerId;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {

        if (deployServerId != null) {
            try {
                File settings = new File("settings.xml");
                FileWriter w = new FileWriter(settings);

                w.write("<settings xmlns=\"http://maven.apache.org/SETTINGS/1.0.0\"\n" +
                        "          xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                        "          xsi:schemaLocation=\"http://maven.apache.org/SETTINGS/1.0.0\n" +
                        "                      http://maven.apache.org/xsd/settings-1.0.0.xsd\">");

                w.write("<servers>");
                w.write("<server>\n" +
                        "<id>" + deployServerId + "</id>\n" +
                        "<username>" + deployUsername + "</username>\n" +
                        "<password>" + deployPassword + "</password>\n" +
                        "</server>");
                w.write("</servers>");
                w.write("</settings>");
                w.flush();
                w.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
