pipeline{
  agent none
  options {
    skipDefaultCheckout true
  }
  stages {
    stage("Checkout Code from Gitlab "){
      agent {
        label 'master'
      }
      steps {
        checkout([
               $class: 'GitSCM',
               branches: [[name: '*/devops1_spring']],
               doGenerateSubmoduleConfigurations: false,
               extensions: [[$class: 'LocalBranch', localBranch: "**"]],
               submoduleCfg: [],
               userRemoteConfigs: [[credentialsId: '736051fa-c90e-4823-8b1b-cc861e19f951',
               url:'git@gitlab.rabat.sqli.com:DEVOPS_TRAINING/DEVOPS_TRAINING.git']]])
      }
    }
    stage("Build") {
           parallel {
                stage('Build Catalog App') {
                    agent {
                        label "master"
                    }
                    steps {
                        sh "make build_dev"
                        sh "make build_catalog"
                    }
                }
                stage('Build Shopfront App') {
                    agent {
                        label "master"
                    }
                    steps {
                        sh "make buil_dfront"
                    }
                }
                stage('Build Stockmanager App') {
                    agent {
                        label "master"
                    }
                    steps {
                        sh "make buil_dstock"
                    }
                }
            }
    }

    stage("Unit Tests") {
           parallel {
                stage('Unit Tests Catalog App') {
                    agent {
                        label "master"
                    }
                    steps {
                        sh "make test_catalog"
                    }
                }
                stage('Unit Tests Shopfront App') {
                    agent {
                        label "master"
                    }
                    steps {
                        sh "make test_front"
                    }
                }
                stage('Unit Tests Stockmanager App') {
                    agent {
                        label "master"
                    }
                    steps {
                        sh "make test_stock"
                    }
                }
            }
    }

    stage("Deploy Artifacts to Nexus") {
           parallel {
                stage('Deploy Catalog App Artifacts') {
                    agent {
                        label "master"
                    }
                    steps {
                        sh "make deploy_catalog"
                    }
                }
                stage('Deploy Shopfront App Artifacts') {
                    agent {
                        label "master"
                    }
                    steps {
                        sh "make deploy_front"
                    }
                }
                stage('Deploy Stockmanager App Artifacts') {
                    agent {
                        label "master"
                    }
                    steps {
                        sh "make deploy_stock"
                    }
                }
            }
    }

    stage("Build Release Images") {
           parallel {
                stage('Build Catalog App Release Image') {
                    agent {
                        label "master"
                    }
                    steps {
                        sh "make release_catalog"
                    }
                }
                stage('Build Shopfront App Release Image') {
                    agent {
                        label "master"
                    }
                    steps {
                        sh "make release_front"
                    }
                }
                stage('Build Stockmanager App Release Image') {
                    agent {
                        label "master"
                    }
                    steps {
                        sh "make release_stock"
                    }
                }
            }
    }

    stage("Running Functional Tests"){
      agent {
          label "master"
      }
      steps {
          sh "make functional_test"
      }
      post {
        success {
          sh "make functional_down"
        }
      }
    }

    stage("Publish Release Images to Registry") {
           parallel {
                stage('Publish Catalog App Release Image') {
                    agent {
                        label "master"
                    }
                    steps {
                        sh "make publish_catalog"
                    }
                }
                stage('Publish Shopfront App Release Image') {
                    agent {
                        label "master"
                    }
                    steps {
                        sh "make publish_front"
                    }
                }
                stage('Publish Stockmanager App Release Image') {
                    agent {
                        label "master"
                    }
                    steps {
                        sh "make publish_stock"
                    }
                }
            }
    }

    stage("Running Performance Tests"){
      agent {
          label "Slave1"
      }
      steps {
          sh "make performance_test"
      }
      post {

        failure {
          sh "make functional_down"
          emailext(
            subject: "Performance Tests on ${env.JOB_NAME} [${env.BUILD_NUMBER}] Failed!",
            body: """<p> "Performance Tests on ${env.JOB_NAME} [${env.BUILD_NUMBER}] Failed!":</p>
            <p>Check console output at &QUOT;<a href='${env.BUILD_URL}'>${env.JOB_NAME} [${env.BUILD_NUMBER}]</a>&QUOT;</p>""",
            to: "ayoub@example.com"
          )
        }
        success {
          sh "make functional_down"
          emailext(
            subject: "Performance Tests on ${env.JOB_NAME} [${env.BUILD_NUMBER}] Passed!",
            body: """<p> "Performance Tests on ${env.JOB_NAME} [${env.BUILD_NUMBER}] Passed!":</p>
            <p>Check console output at &QUOT;<a href='${env.BUILD_URL}'>${env.JOB_NAME} [${env.BUILD_NUMBER}]</a>&QUOT;</p>""",
            to: "ayoub@example.com"
          )
        }
      }
    }

    stage("Provisioning AWS Infrastructure - Terraform"){
      agent {
          label "master"
      }
      steps {
          echo "Provisionning AWS infrastructure with Terraform"
      }
    }

   stage("Running Image on DockerSwarm"){
      agent {
        label 'Master1'
      }
      steps {
        sh "make swarm"
      }
   }

 }
  post {
    failure {
        sh "docker-compose -f docker/dev/docker-compose-test.yml down"
        sh "docker-compose -f docker/dev/docker-compose.yml down"
        sh "docker-compose -f docker/dev/docker-compose-deploy.yml down"
    }
  }
}
}
