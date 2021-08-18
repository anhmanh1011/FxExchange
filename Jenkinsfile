pipeline {
    agent any
    tools {
        maven 'maven 3.8.1'
        jdk 'JDK 11'
    }

    stages {

        stage ('Initialize') {
            steps {
                sh '''
                    echo "PATH = ${PATH}"
                    echo "M2_HOME = ${M2_HOME}"
                '''
            }
        }
        stage('build') {
            steps {
                sh('mvn clean install -DskipTests=false -X')
            }
        }
        stage('add_Permission') {
            steps {
                sh('chmod -R 777 *')
            }
        }
        stage('build_docker') {
            steps {
                sh('docker build -t order_fx .')
            }
        }

        stage('docker_run') {

            steps {
                script {

                    try {
                        String imageExists = sh(script: 'docker ps -a --filter "name=order_fx"', returnStdout: true)
                        echo imageExists;
                        if (imageExists != null && imageExists.contains('order_fx')) {
                            echo 'Ton tai container old version';
//                            String isStop = sh(script: 'docker stop demo_jenkins"', returnStdout: true)
//                            if (isStop != null && isStop != '') {
//                                echo 'stop successfully';
//                            }
                            String isRemoveContainerOldVersion = sh(script: 'docker rm --force $(docker ps -a --filter name=order_fx -q)', returnStdout: true)
                            if (isRemoveContainerOldVersion != null && isRemoveContainerOldVersion != '') {
                                echo 'remove successfully'
                            }
                        } else
                            echo 'khong ton tai';
                    } catch (Exception ex) {
                        echo ex;
                    }

                }

                sh(script: 'docker run  --name order_fx -d -p 8090:8090 -v /home:/logs order_fx', returnStdout: true)
                sh(script: 'docker rmi $(docker images -f "dangling=true" -q)', returnStdout: true)
            }
        }

    }
}
