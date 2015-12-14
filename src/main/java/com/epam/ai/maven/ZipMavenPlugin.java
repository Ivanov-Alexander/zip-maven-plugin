package com.epam.ai.maven;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.project.MavenProject;
import org.zeroturnaround.zip.ZipUtil;

import java.io.File;
import java.util.Map;
import java.util.stream.Stream;

@Mojo(name = "zip")
public class ZipMavenPlugin extends AbstractMojo {
	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		Map context = getPluginContext();
		//PluginDescriptor pluginDescriptor = (PluginDescriptor) context.get("pluginDescriptor");
		MavenProject project = (MavenProject) context.get("project");

		String finalName = project.getBuild().getFinalName();
		String target = project.getBuild().getDirectory();
		File[] files = new File(target).listFiles((dir, name) -> name.startsWith(finalName));

		Stream.of(files).forEach((file) -> ZipUtil.packEntry(file, new File(target + "/" + finalName + ".zip")));
	}
}
