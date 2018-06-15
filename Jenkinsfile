pipeline {
    agent {
        label 'master'
    }
    options {
        skipDefaultCheckout false
    }
    stages {
        stage("Checkout Code from Github") {
            steps {
                echo "CHECKOUT DONE"
            }
        }

        stage("Init") {
            steps {
                // sh "docker network create labnet 2> /dev/null"
                sh "make clean"
                // sh "ansible-playbook init.yml"
                sh "curl -v --user '${nexus_user}:${nexus_password}' --upload-file ./docker/deploy/swarm/stack.yml ${NEXUS_URL}/repository/stacks/stack.yml"
                sh "curl -v --user '${nexus_user}:${nexus_password}' --upload-file ./docker/deploy/kubernetes/stockmanager-service.yaml ${NEXUS_URL}/repository/kubernetes/stockmanager-service.yaml"
                sh "curl -v --user '${nexus_user}:${nexus_password}' --upload-file ./docker/deploy/kubernetes/productcatalogue-service.yaml ${NEXUS_URL}/repository/kubernetes/productcatalogue-service.yaml"
                sh "curl -v --user '${nexus_user}:${nexus_password}' --upload-file ./docker/deploy/kubernetes/shopfront-service.yaml ${NEXUS_URL}/repository/kubernetes/kubernetes-dashboard.yaml"
                sh "curl -v --user '${nexus_user}:${nexus_password}' --upload-file ./docker/playbooks/inventory ${NEXUS_URL}/repository/playbooks/inventory"
                sh "curl -v --user '${nexus_user}:${nexus_password}' --upload-file ./docker/playbooks/swarm_stack.yml ${NEXUS_URL}/repository/playbooks/swarm_stack.yml"
                sh "curl -v --user '${nexus_user}:${nexus_password}' --upload-file ./docker/playbooks/swarm_stack_clean.yml ${NEXUS_URL}/repository/playbooks/swarm_playbook_clean.yml"
            }
        }

        stage("Init Test Database") {
            steps {
                sh "docker-compose -f ./docker/dev/docker-compose.yml build agent"
                sh "docker-compose -f ./docker/dev/docker-compose.yml build db"
                sh "docker-compose -f ./docker/dev/docker-compose.yml  run --rm agent"
//            sh "docker-compose -f ./docker/dev/docker-compose.yml exec -T db sh -c 'mysql -u ${DB_USER} -p${DB_PASSWORD} springdocker < /opt/dumps/springdocker.sql' "
            }
        }

        stage("Build") {
            parallel {
                stage('Build Catalog App') {
                    steps {
                        sh "docker-compose -f ./docker/dev/docker-compose.yml build  --no-cache catalog"
                        sh "docker-compose -f ./docker/dev/docker-compose.yml run --rm catalog"
                    }

                }
                stage('Build Shopfront App') {
                    steps {
                        sh '''docker-compose -f docker/dev/docker-compose.yml build --no-cache  front
                        docker-compose -f docker/dev/docker-compose.yml run --rm front'''
                    }

                }
                stage('Build Stockmanager App') {
                    steps {
                        sh "docker-compose -f ./docker/dev/docker-compose.yml build --no-cache  stock"
                        sh "docker-compose -f docker/dev/docker-compose.yml run --rm stock"
                    }
                }
            }

        }

        stage("Unit Tests") {
            parallel {
                stage('Unit Tests Catalog App') {
                    steps {
                        sh 'docker-compose -f docker/dev/docker-compose-test.yml run --rm catalog'
                    }


                }
                stage('Unit Tests Shopfront App') {
                    steps {
                        sh 'docker-compose -f docker/dev/docker-compose-test.yml run --rm front'
                        // junit 'shopfront/target/surefire-reports/**/*.xml'
                    }


                }
                stage('Unit Tests Stockmanager App') {
                    steps {
                        sh 'docker-compose -f docker/dev/docker-compose-test.yml run --rm stock'
                        // junit 'stockmanager/target/surefire-reports/**/*.xml'
                    }


                }
            }

        }

        stage("Coverage Tests") {
            parallel {
                stage('Coverage Tests Catalog App') {
                    steps {
                        sh 'docker-compose -f docker/dev/docker-compose-coverage.yml run catalog'
                    }

                }
                stage('Coverage Tests Shopfront App') {
                    steps {
                        sh 'docker-compose -f docker/dev/docker-compose-coverage.yml run front'
                    }

                }
                stage('Coverage Tests Stockmanager App') {
                    steps {
                        sh 'docker-compose -f docker/dev/docker-compose-coverage.yml run stock'
                    }

                }
            }
        }

        stage("Upload Artifacts to Nexus") {
            parallel {
                stage('Deploy Catalog App Artifacts') {

                    steps {
                        sh 'docker-compose -f docker/dev/docker-compose-deploy.yml run catalog'
                    }


                }
                stage('Deploy Shopfront App Artifacts') {

                    steps {
                        sh 'docker-compose -f docker/dev/docker-compose-deploy.yml run front'
                    }


                }
                stage('Deploy Stockmanager App Artifacts') {

                    steps {
                        sh 'docker-compose -f docker/dev/docker-compose-deploy.yml run stock'
                    }


                }
            }

        }

        stage("Build Release Images") {
            parallel {
                stage('Build Catalog Release Image') {

                    steps {
                        echo "Building Catalog Release Image"
                        sh "docker pull ayoubensalem/springcatalog:latest"
                        // sh "docker-compose -f docker/release/docker-compose.yml build --no-cache  productcatalogue "
                    }

                }
                stage('Build Shopfront Release Image') {

                    steps {
                        echo "Build Shopfront Release Image"
                        sh "docker pull ayoubensalem/springfront:latest"
                        // sh "docker-compose -f docker/release/docker-compose.yml build --no-cache shopfront"
                    }

                }
                stage('Build Stockmanager  Release Image') {

                    steps {
                        echo "Build Stockmanager Release Image"     
                        sh "docker pull docker push ayoubensalem/springstock:latest"                   
                        // sh "docker-compose -f docker/release/docker-compose.yml build --no-cache stockmanager"
                    }

                }
                stage('Build Database Image') {

                    steps {
                        sh "docker pull docker push ayoubensalem/springmysql:latest"
                        // sh "docker-compose -f docker/release/docker-compose.yml build  db"

                    }

                }
            }

        }

        stage("Upload Release Images") {
            parallel {
                stage('Publish Catalog App Release Image') {

                    steps {
                        echo "Deploy to Nexus Docker registry"
                        // sh "docker login -u ${docker_registry_id} -p ${docker_registry_password}"
                        // sh "docker push ayoubensalem/springcatalog:latest"

                    }


                }
                stage('Publish Shopfront App Release Image') {

                    steps {
                        echo "Deploy to Nexus Docker registry"
                        // sh "docker push ayoubensalem/springfront:latest"
                }

                }
                stage('Publish Stockmanager App Release Image') {

                    steps {
                        echo "Deploy to Nexus Docker registry"
                        // sh "docker push ayoubensalem/springstock:latest"
                    }

                }
                stage('Publish DB Release Image') {

                    steps {
                        echo "Deploy to Nexus Docker registry"
                        // sh "docker push ayoubensalem/springmysql:latest"
                    }

                }
            }
        }

        stage("Provisioning Swarm stack") {
            agent {
                label 'DOMaster'
            }
            steps {
                echo "Provision swarm stack"
                sh "wget  -O swarm_stack.yml ${NEXUS_URL}/repository/playbooks/swarm_stack.yml"
                sh "wget -O swarm_playbook_clean.yml ${NEXUS_URL}/repository/playbooks/swarm_playbook_clean.yml"
                sh "wget -O inventory ${NEXUS_URL}/repository/playbooks/inventory"
                // sh "wget -O inventory https://transfer.sh/cILeF/inventory"
                // sh "wget -O swarm_playbook.yml https://transfer.sh/9Jk1A/swarm_stack.yml"
                // sh "wget -O swarm_playbook_clean.yml https://transfer.sh/alnWy/swarm_stack_clean.yml"
                // sh "ansible-playbook -i inventory swarm_playbook_clean.yml"
                // sh "ansible-playbook -i inventory swarm_stack.yml --extra-vars 'inventory_hostname=${INVENTORY_HOSTNAME}'"
                // sh '''sudo docker network prune --force
                //     sudo docker image prune --filter dangling=true -f
                //     sudo  docker container prune -f'''
                // sh "sudo docker stack rm MyApp"
            }
        }

//         stage("Deploying Swarm Stack ") {
//             agent {
//                 label 'DOMaster'
//             }
//             steps {
// //          sh "wget -O stack.yml '${NEXUS_URL}/repository/stacks/stack.yml'"
//                 // sh "wget -O stack.yml 'https://transfer.sh/tu084/stack.yml'"
//                 // sh "sudo docker stack deploy -c stack.yml MyApp"
//                 sh "sleep 90"
// //          sh "docker exec -t db sh -c 'mysql -u ${DB_USER} -p${DB_PASSWORD} springdocker < /opt/dumps/springdocker.sql' "
//             }
//         }

        stage("Functional Tests") {
            steps {
                // sh '''docker-compose -f docker/functional-tests/docker-compose.yml build --no-cache
                //     docker-compose -f docker/functional-tests/docker-compose.yml run test'''
                // junit "docker/functional-tests/target/surefire-reports/**/*.xml"
                echo "Functaional Tests"
            //    sh "echo 'Proxy Problem'"
            }
            post {
                always {
                    sh "make clean"
                }
            }
        }

        stage("Building Performance Image") {
            steps {
                echo "build performance image"
//          sh '''docker build -t ayoubensalem/spring:performance docker/performance-tests
//                docker tag ayoubensalem/spring:performance nexus.dev:18444/spring-performance
//                docker push nexus.dev:18444/spring-performance '''
            }
            post {
                always {
                    echo "Hello"
//          sh "make clean"
                }
            }
        }

        stage("Running Performance Tests") {
            steps {
                // sh "docker rm -f \$(docker ps -aq) >/dev/null 2>&1 || true"
                 echo "Running Performance Image"
                // sh "docker pull ayoubensalem/spring-performance:latest"
                sh "docker run -d -p 33733:80 --rm ayoubensalem/spring-performance:latest"
            }
            post {
                always {
                    emailext(
                            subject: "Performance Tests on ${env.JOB_NAME} [${env.BUILD_NUMBER}] Finished!",
                            body: """<p> "Performance Tests on ${env.JOB_NAME} [${env.BUILD_NUMBER}] Finished!":</p>
                <p>Check console output at &QUOT;<a href='${env.BUILD_URL}'>${env.JOB_NAME} [${
                                env.BUILD_NUMBER
                            }]</a>&QUOT;</p>""",
                            to: "ayoub@example.com"
                    )
                }
                failure {
                    sh "docker rm -f \$(docker ps -aq) >/dev/null 2>&1 || true"
                    sh '''docker network prune --force
                docker image prune --filter dangling=true -f
                docker container prune -f'''
                }
            }
        }

        // stage("Init DB in Kuberenetes"){
        //     agent {
        //         label "KubeMaster"
        //     }
        //     steps {
        //         sh "kubectl delete deployment --all"
        //         sh "kubectl delete svc --all"
        //         sh "wget -O kube.tar.gz https://transfer.sh/SdOam/kube.tar.gz"
        //         sh "rm -rf /tmp/kubernetes"
        //         sh "tar xzvf kube.tar.gz -C /tmp"
        //         sh "kubectl apply -f /tmp/kubernetes/mysql-service.yaml"
        //     }
        // }


        // stage("Deploy to Kubernetes") {
        //     parallel {
        //         stage('Deploy Catalog App') {
        //             agent {
        //                 label 'KubeMaster'
        //             }
        //             steps {
        //                 sh "kubectl apply -f /tmp/kubernetes/productcatalogue-service.yaml"
        //             }

        //         }
        //         stage('Deploy Shopfront App') {
        //             agent {
        //                 label 'KubeMaster'
        //             }
        //             steps {
        //                 sh "kubectl apply -f /tmp/kubernetes/shopfront-service.yaml"
        //             }

        //         }
        //         stage('Deploy Stockmanager App') {
        //             agent {
        //                 label 'KubeMaster'
        //             }
        //             steps {
        //                 sh "kubectl apply -f /tmp/kubernetes/stockmanager-service.yaml"
        //             }

        //         }
        //     }
        // }
    }
    post {
        always {
            emailext(
                    subject: "Pipelinde ${env.JOB_NAME} [${env.BUILD_NUMBER}] Finished!",
                    body: """<p> "Performance Tests on ${env.JOB_NAME} [${env.BUILD_NUMBER}] Finished!":</p>
                <p>Check console output at &QUOT;<a href='${env.BUILD_URL}'>${env.JOB_NAME} [${
                        env.BUILD_NUMBER
                    }]</a>&QUOT;</p>""",
                    to: "ayoub@example.com"
            )
            sh "make clean"
        }
    }

}
