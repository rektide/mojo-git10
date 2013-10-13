package com.voodoowarez.mojo.git10;

import java.io.File;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;

/**
* Goal which grabs a version of a tree
*
* @goal checkout
* @phase process-sources
*/
@Mojo(name = "git10")
public class Git10Mojo extends AbstractMojo
{
	/**
	* Repository address
	*/
	@Parameter(required=true)
	private String repository;

	/**
	* Branch or Tag to checkout
	*/
	@Parameter
	private String branchOrTag;

	/**
	* 
	*/
	@Parameter
	private String outputName;

	/**
	* Name to use for remote repository.
	*/
	@Parameter
	private String remoteName;

	/**
	* Check out only asked for branch.
	*/
	@Parameter(defaultValue= "true")
	private Boolean basic;

	/**
	* Location of the file.
	* @parameter default-value="${project.build.outputDirectory}/generated-git10"
	*/
	private File outputDirectory;

	public void execute() throws MojoExecutionException{
		try{
			clone();
		}catch(Exception ex){
			throw new MojoExecutionException("Failed checkout",ex);
		}
	}
	
	protected void clone(Repository reop) throws Exception{
		// setup
		final CloneCommand clone = Git.cloneRepository();
		clone.setURI(this.repository);
		clone.setDirectory(this.outputDirectory);
		clone.setBranch(this.branchOrTag);
		clone.setRemote(this.remoteName);
		clone.setCloneAllBranches(!basic);
		//UsernamePasswordCredentialsProvider user = new UsernamePasswordCredentialsProvider(login, password);
		//clone.setCredentialsProvider(user);
		
		// clone
		clone.call().getRepository().close();
	}
}
