pipeline {
    agent any
    tools { 
        jdk 'JDK8' 
        maven 'MAVEN3'
    }
    triggers {
        pollSCM('H/5 * * * *')
        upstream(upstreamProjects: "pipelines/openchrom3rdpl/${BRANCH_NAME}", threshold: hudson.model.Result.SUCCESS)
    }
    options {
        disableConcurrentBuilds()
        buildDiscarder(logRotator(numToKeepStr: '5'))
    }
    stages {
    	stage('checkout') {
    	steps {
    			dir('releng') {
					checkout scm
					//windows 32+64
					sh 'wget -nv https://github.com/OpenChrom/openchromcomp/raw/develop/openchrom/packaging/net.openchrom.rcp.compilation.community.packaging/build/jre/jre-8u102-windows-i586.tar.gz'
					sh 'wget -nv https://github.com/OpenChrom/openchromcomp/raw/develop/openchrom/packaging/net.openchrom.rcp.compilation.community.packaging/build/jre/jre-8u102-windows-x64.tar.gz'
					sh 'tar -xvzf jre-8u102-windows-i586.tar.gz -C openchrom/features/net.openchrom.jre.win32.win32.x86.feature/jre'
					sh 'tar -xvzf jre-8u102-windows-x64.tar.gz -C openchrom/features/net.openchrom.jre.win32.win32.x86_64.feature/jre'
					//linux x86
					sh 'wget -nv https://cdn.azul.com/zulu/bin/zulu8.30.0.1-jdk8.0.172-linux_i686.tar.gz'
					sh 'tar -xvzf zulu8.30.0.1-jdk8.0.172-linux_i686.tar.gz -C openchrom/features/net.openchrom.jre.linux.gtk.x86.feature/jre'
					sh 'chmod +x openchrom/features/net.openchrom.jre.linux.gtk.x86.feature/jre/zulu8.30.0.1-jdk8.0.172-linux_i686/bin/*'
					//linux x86_64
					sh 'wget -nv https://cdn.azul.com/zulu/bin/zulu8.30.0.1-jdk8.0.172-linux_x64.tar.gz'
					sh 'tar -xvzf zulu8.30.0.1-jdk8.0.172-linux_x64.tar.gz -C openchrom/features/net.openchrom.jre.linux.gtk.x86_64.feature/jre'
					sh 'wget -nv https://chriswhocodes.com/downloads/openjfx-8u60-sdk-overlay-linux-amd64.zip'
					sh 'unzip openjfx-8u60-sdk-overlay-linux-amd64.zip -d openchrom/features/net.openchrom.jre.linux.gtk.x86_64.feature/jre'
					sh 'chmod +x openchrom/features/net.openchrom.jre.linux.gtk.x86_64.feature/jre/zulu8.30.0.1-jdk8.0.172-linux_x64/bin/*'
					//mac osx
					sh 'wget -nv https://cdn.azul.com/zulu/bin/zulu8.30.0.1-jdk8.0.172-macosx_x64.tar.gz'
					sh 'tar -xvzf zulu8.30.0.1-jdk8.0.172-macosx_x64.tar.gz -C openchrom/features/net.openchrom.jre.macosx.cocoa.x86_64.feature/jre'
					sh' wget -nv https://chriswhocodes.com/downloads/openjfx-8u60-sdk-overlay-osx-x64.zip'
					sh 'unzip openjfx-8u60-sdk-overlay-osx-x64.zip -d openchrom/features/net.openchrom.jre.macosx.cocoa.x86_64.feature/jre'
					sh 'chmod +x openchrom/features/net.openchrom.jre.macosx.cocoa.x86_64.feature/jre/zulu8.30.0.1-jdk8.0.172-macosx_x64/jre/bin/*'
				}
				dir ('pdfconverter') {
					checkout resolveScm(source: [remote: 'https://github.com/OpenChrom/pdfconverter.git',	$class: 'GitSCMSource', poll: true, extensions: [[$class: 'CheckoutOption', timeout: 240]], , traits: [[$class: 'jenkins.plugins.git.traits.BranchDiscoveryTrait']]], targets: [BRANCH_NAME,'develop'])
				}
				dir ('abifconverter') {
					checkout resolveScm(source: [remote: 'https://github.com/OpenChrom/abifconverter.git',	$class: 'GitSCMSource', poll: true, extensions: [[$class: 'CheckoutOption', timeout: 240]], , traits: [[$class: 'jenkins.plugins.git.traits.BranchDiscoveryTrait']]], targets: [BRANCH_NAME,'develop'])
				}
				dir ('processalignment') {
					checkout resolveScm(source: [remote: 'https://github.com/OpenChrom/processalignment.git',	$class: 'GitSCMSource', poll: true, extensions: [[$class: 'CheckoutOption', timeout: 240]], , traits: [[$class: 'jenkins.plugins.git.traits.BranchDiscoveryTrait']]], targets: [BRANCH_NAME,'develop'])
				}
				dir ('openchromjzy3d') {
					checkout resolveScm(source: [remote: 'https://github.com/OpenChrom/openchromjzy3d.git',	$class: 'GitSCMSource', poll: true, extensions: [[$class: 'CheckoutOption', timeout: 240]], , traits: [[$class: 'jenkins.plugins.git.traits.BranchDiscoveryTrait']]], targets: [BRANCH_NAME,'develop'])
				}
				dir ('cmsconverter') {
					checkout resolveScm(source: [remote: 'https://github.com/OpenChrom/cmsconverter.git',	$class: 'GitSCMSource', poll: true, extensions: [[$class: 'CheckoutOption', timeout: 240]], , traits: [[$class: 'jenkins.plugins.git.traits.BranchDiscoveryTrait']]], targets: [BRANCH_NAME,'develop'])
				}
				dir ('massshiftdetector') {
					checkout resolveScm(source: [remote: 'https://github.com/OpenChrom/massshiftdetector.git',	$class: 'GitSCMSource', poll: true, extensions: [[$class: 'CheckoutOption', timeout: 240]], , traits: [[$class: 'jenkins.plugins.git.traits.BranchDiscoveryTrait']]], targets: [BRANCH_NAME,'develop'])
				}
				dir ('netcdfmschrom') {
					checkout resolveScm(source: [remote: 'https://github.com/OpenChrom/netcdfmschrom.git',	$class: 'GitSCMSource', poll: true, extensions: [[$class: 'CheckoutOption', timeout: 240]], , traits: [[$class: 'jenkins.plugins.git.traits.BranchDiscoveryTrait']]], targets: [BRANCH_NAME,'develop'])
				}
				dir ('processorchrom2d') {
					checkout resolveScm(source: [remote: 'https://github.com/OpenChrom/processorchrom2d.git',	$class: 'GitSCMSource', poll: true, extensions: [[$class: 'CheckoutOption', timeout: 240]], , traits: [[$class: 'jenkins.plugins.git.traits.BranchDiscoveryTrait']]], targets: [BRANCH_NAME,'develop'])
				}
				dir ('opentyper') {
					checkout resolveScm(source: [remote: 'https://github.com/OpenChrom/opentyper.git',	$class: 'GitSCMSource', poll: true, extensions: [[$class: 'CheckoutOption', timeout: 240]], , traits: [[$class: 'jenkins.plugins.git.traits.BranchDiscoveryTrait']]], targets: [BRANCH_NAME,'develop'])
				}
				dir ('msqbatlibs') {
					checkout resolveScm(source: [remote: 'https://github.com/OpenChrom/msqbatlibs.git',	$class: 'GitSCMSource', poll: true, extensions: [[$class: 'CheckoutOption', timeout: 240]], , traits: [[$class: 'jenkins.plugins.git.traits.BranchDiscoveryTrait']]], targets: [BRANCH_NAME,'develop'])
				}
				dir ('processortracecompare') {
					checkout resolveScm(source: [remote: 'https://github.com/OpenChrom/processortracecompare.git',	$class: 'GitSCMSource', poll: true, extensions: [[$class: 'CheckoutOption', timeout: 240]], , traits: [[$class: 'jenkins.plugins.git.traits.BranchDiscoveryTrait']]], targets: [BRANCH_NAME,'develop'])
				}
				dir ('xmutidemscontrol') {
					checkout resolveScm(source: [remote: 'https://github.com/OpenChrom/xmutidemscontrol.git',	$class: 'GitSCMSource', poll: true, extensions: [[$class: 'CheckoutOption', timeout: 240]], , traits: [[$class: 'jenkins.plugins.git.traits.BranchDiscoveryTrait']]], targets: [BRANCH_NAME,'develop'])
				}
				dir ('batmassprocessheatmap') {
					checkout resolveScm(source: [remote: 'https://github.com/OpenChrom/batmassprocessheatmap.git',	$class: 'GitSCMSource', poll: true, extensions: [[$class: 'CheckoutOption', timeout: 240]], , traits: [[$class: 'jenkins.plugins.git.traits.BranchDiscoveryTrait']]], targets: [BRANCH_NAME,'develop'])
				}
				dir ('netcdfchromfid') {
					checkout resolveScm(source: [remote: 'https://github.com/OpenChrom/netcdfchromfid.git',	$class: 'GitSCMSource', poll: true, extensions: [[$class: 'CheckoutOption', timeout: 240]], , traits: [[$class: 'jenkins.plugins.git.traits.BranchDiscoveryTrait']]], targets: [BRANCH_NAME,'develop'])
				}
				dir ('openchromjython') {
					checkout resolveScm(source: [remote: 'https://github.com/OpenChrom/openchromjython.git',	$class: 'GitSCMSource', poll: true, extensions: [[$class: 'CheckoutOption', timeout: 240]], , traits: [[$class: 'jenkins.plugins.git.traits.BranchDiscoveryTrait']]], targets: [BRANCH_NAME,'develop'])
				}
				dir ('knimeconnector') {
					checkout resolveScm(source: [remote: 'https://github.com/OpenChrom/knimeconnector.git',	$class: 'GitSCMSource', poll: true, extensions: [[$class: 'CheckoutOption', timeout: 240]], , traits: [[$class: 'jenkins.plugins.git.traits.BranchDiscoveryTrait']]], targets: [BRANCH_NAME,'develop'])
				}
				dir ('openchromcdksupport') {
					checkout resolveScm(source: [remote: 'https://github.com/OpenChrom/openchromcdksupport.git',	$class: 'GitSCMSource', poll: true, extensions: [[$class: 'CheckoutOption', timeout: 240]], , traits: [[$class: 'jenkins.plugins.git.traits.BranchDiscoveryTrait']]], targets: [BRANCH_NAME,'develop'])
				}
				dir ('cmsworkflow') {
					checkout resolveScm(source: [remote: 'https://github.com/OpenChrom/cmsworkflow.git',	$class: 'GitSCMSource', poll: true, extensions: [[$class: 'CheckoutOption', timeout: 240]], , traits: [[$class: 'jenkins.plugins.git.traits.BranchDiscoveryTrait']]], targets: [BRANCH_NAME,'develop'])
				}
				dir ('rscriptingsupport') {
					checkout resolveScm(source: [remote: 'https://github.com/OpenChrom/rscriptingsupport.git',	$class: 'GitSCMSource', poll: true, extensions: [[$class: 'CheckoutOption', timeout: 240]], , traits: [[$class: 'jenkins.plugins.git.traits.BranchDiscoveryTrait']]], targets: [BRANCH_NAME,'develop'])
				}
				dir ('mgfconverter') {
					checkout resolveScm(source: [remote: 'https://github.com/OpenChrom/mgfconverter.git',	$class: 'GitSCMSource', poll: true, extensions: [[$class: 'CheckoutOption', timeout: 240]], , traits: [[$class: 'jenkins.plugins.git.traits.BranchDiscoveryTrait']]], targets: [BRANCH_NAME,'develop'])
				}
				dir ('geneident') {
					checkout resolveScm(source: [remote: 'https://github.com/OpenChrom/GeneIdent.git',	$class: 'GitSCMSource', poll: true, extensions: [[$class: 'CheckoutOption', timeout: 240]], , traits: [[$class: 'jenkins.plugins.git.traits.BranchDiscoveryTrait']]], targets: [BRANCH_NAME,'develop'])
				}
				dir ('openchromgroovy') {
					checkout resolveScm(source: [remote: 'https://github.com/OpenChrom/openchromgroovy.git',	$class: 'GitSCMSource', poll: true, extensions: [[$class: 'CheckoutOption', timeout: 240]], , traits: [[$class: 'jenkins.plugins.git.traits.BranchDiscoveryTrait']]], targets: [BRANCH_NAME,'develop'])
				}
				dir ('compmspbm') {
					checkout resolveScm(source: [remote: 'https://github.com/OpenChrom/compmspbm.git',	$class: 'GitSCMSource', poll: true, extensions: [[$class: 'CheckoutOption', timeout: 240]], , traits: [[$class: 'jenkins.plugins.git.traits.BranchDiscoveryTrait']]], targets: [BRANCH_NAME,'develop'])
				}
				dir ('ulan-openchrom') {
					checkout resolveScm(source: [remote: 'https://github.com/OpenChrom/ulan-openchrom.git',	$class: 'GitSCMSource', poll: true, extensions: [[$class: 'CheckoutOption', timeout: 240]], , traits: [[$class: 'jenkins.plugins.git.traits.BranchDiscoveryTrait']]], targets: [BRANCH_NAME,'develop'])
				}
			}
    	}
		stage('build') {
			steps {
					sh 'mvn -B -Dmaven.repo.local=.repository -f releng/openchrom/cbi/pom.xml install'
					sh 'mvn -B -Dmaven.repo.local=.repository -f pdfconverter/openchrom/cbi/net.openchrom.msd.converter.supplier.pdf.cbi/pom.xml install'
					sh 'mvn -B -Dmaven.repo.local=.repository -f abifconverter/openchrom/cbi/net.openchrom.wsd.converter.supplier.abif.cbi/pom.xml install'
					sh 'mvn -B -Dmaven.repo.local=.repository -f processalignment/openchrom/cbi/net.openchrom.chromatogram.xxd.process.supplier.alignment.cbi/pom.xml install'
					sh 'mvn -B -Dmaven.repo.local=.repository -f openchromjzy3d/openchrom/cbi/net.openchrom.chromatogram.msd.process.supplier.jzy3d.cbi/pom.xml install'
					sh 'mvn -B -Dmaven.repo.local=.repository -f cmsconverter/openchrom/cbi/net.openchrom.msd.converter.supplier.cms.cbi/pom.xml install'
					sh 'mvn -B -Dmaven.repo.local=.repository -f massshiftdetector/openchrom/cbi/net.openchrom.chromatogram.msd.processor.supplier.massshiftdetector.cbi/pom.xml install'
					sh 'mvn -B -Dmaven.repo.local=.repository -f netcdfmschrom/chemclipse/cbi/net.openchrom.msd.converter.supplier.cdf.cbi/pom.xml install'
					sh 'mvn -B -Dmaven.repo.local=.repository -f processorchrom2d/openchrom/cbi/net.openchrom.xxd.processor.supplier.chrom2d.cbi/pom.xml install'
					sh 'mvn -B -Dmaven.repo.local=.repository -f opentyper/openchrom/cbi/net.openchrom.msd.identifier.supplier.opentyper.cbi/pom.xml install'
					sh 'mvn -B -Dmaven.repo.local=.repository -f msqbatlibs/openchrom/cbi/net.openchrom.msqbatlibs.cbi/pom.xml install'
					sh 'mvn -B -Dmaven.repo.local=.repository -f processortracecompare/openchrom/cbi/net.openchrom.xxd.processor.supplier.tracecompare.cbi/pom.xml install'
					sh 'mvn -B -Dmaven.repo.local=.repository -f xmutidemscontrol/chemclipse/cbi/cn.edu.xmu.tidems.control.cbi/pom.xml install'
					sh 'mvn -B -Dmaven.repo.local=.repository -f batmassprocessheatmap/openchrom/cbi/org.batmass.xxd.process.supplier.heatmap.cbi/pom.xml install'
					sh 'mvn -B -Dmaven.repo.local=.repository -f netcdfchromfid/chemclipse/cbi/net.openchrom.csd.converter.supplier.cdf.cbi/pom.xml install'
					sh 'mvn -B -Dmaven.repo.local=.repository -f openchromjython/openchrom/cbi/net.openchrom.chromatogram.msd.process.supplier.jython.cbi/pom.xml install'
					sh 'mvn -B -Dmaven.repo.local=.repository -f openchromcdksupport/chemclipse/cbi/net.openchrom.chromatogram.msd.identifier.supplier.cdk.cbi/pom.xml install'
					sh 'mvn -B -Dmaven.repo.local=.repository -f cmsworkflow/openchrom/cbi/net.openchrom.msd.process.supplier.cms.cbi/pom.xml install'
					sh 'mvn -B -Dmaven.repo.local=.repository -f rscriptingsupport/openchrom/cbi/net.openchrom.xxd.processor.supplier.rscripting.cbi/pom.xml install'
					sh 'mvn -B -Dmaven.repo.local=.repository -f mgfconverter/openchrom/cbi/net.openchrom.msd.converter.supplier.mgf.cbi/pom.xml install'
					sh 'mvn -B -Dmaven.repo.local=.repository -f geneident/openchrom/cbi/net.openchrom.wsd.identifier.supplier.geneident.cbi/pom.xml install'
					sh 'mvn -B -Dmaven.repo.local=.repository -f openchromgroovy/openchrom/cbi/net.openchrom.chromatogram.msd.process.supplier.groovy.cbi/pom.xml install'
					sh 'mvn -B -Dmaven.repo.local=.repository -f compmspbm/openchrom/cbi/net.openchrom.chromatogram.msd.comparison.supplier.pbm.cbi/pom.xml install'
					sh 'mvn -B -Dmaven.repo.local=.repository -f ulan-openchrom/chemclipse/cbi/org.chromulan.system.control.cbi/pom.xml install'

			}
		}
		stage('package') {
			steps {
				sh 'mvn -B -Dmaven.repo.local=.repository -f releng/openchrom/features/pom.xml install'
				sh 'mvn -B -Dmaven.repo.local=.repository -f releng/openchrom/sites/pom.xml install'
			}
		}
		stage('deploy') {
			when { branch 'develop' }
			steps {
				withCredentials([string(credentialsId: 'DEPLOY_HOST', variable: 'DEPLOY_HOST')]) {
					sh 'scp -r releng/openchrom/sites/openchrom.platform/target/site/* '+"${DEPLOY_HOST}community/latest/platform"
				}
			}
		}
    }
    post {
    	always {
    	    junit allowEmptyResults: true, testResults: '**/target/surefire-reports/*.xml'
    	    warnings canRunOnFailed: true, consoleParsers: [[parserName: 'Maven']], shouldDetectModules: true
    	    openTasks canRunOnFailed: true, ignoreCase: true, high: 'FIXME', low: 'XXX', normal: 'TODO', pattern: '**/*.java', shouldDetectModules: true
    	}
        failure {
            emailext(body: '${DEFAULT_CONTENT}', mimeType: 'text/html',
		         replyTo: '$DEFAULT_REPLYTO', subject: '${DEFAULT_SUBJECT}',
		         to: emailextrecipients([[$class: 'CulpritsRecipientProvider'],
		                                 [$class: 'RequesterRecipientProvider']]))
        }
        success {
            cleanWs notFailBuild: true
        }

    }
}