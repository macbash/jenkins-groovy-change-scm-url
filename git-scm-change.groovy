//Created by Mohammed Azfar
//Scope, you can use this script to change all jenkins job to New SCM
//No Manual Intervention is required :D
//** Before executing trial in your mock environmens **

import hudson.plugins.git.*
import jenkins.*
import jenkins.model.*

def modifyGitUrl(url) {
  def newurl = url.replace("github.com", "bitbucket.com")
  return newurl
}

Jenkins.instance.items.each {
  if (it.scm instanceof GitSCM) {
    def oldScm = it.scm
    def newUserRemoteConfigs = oldScm.userRemoteConfigs.collect {
      new UserRemoteConfig(modifyGitUrl(it.url), it.name, it.refspec, it.credentialsId)
    }
    def newScm = new GitSCM(newUserRemoteConfigs, oldScm.branches, oldScm.doGenerateSubmoduleConfigurations,
                            oldScm.submoduleCfg, oldScm.browser, oldScm.gitTool, oldScm.extensions)
    it.scm = newScm 
    it.save()
  }
}
