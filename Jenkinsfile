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
                sh('docker build -t OderFx .')
            }
        }

        stage('docker_run') {

            steps {
                script {

                    try {
                        String imageExists = sh(script: 'docker ps --filter "name=oderFx"', returnStdout: true)
                        echo imageExists;
                        if (imageExists != null && imageExists.contains('oderFx')) {
                            echo 'Ton tai container old version';
//                            String isStop = sh(script: 'docker stop demo_jenkins"', returnStdout: true)
//                            if (isStop != null && isStop != '') {
//                                echo 'stop successfully';
//                            }
                            String isRemoveContainerOldVersion = sh(script: 'docker rm --force $(docker ps --filter name=oderFx -q)', returnStdout: true)
                            if (isRemoveContainerOldVersion != null && isRemoveContainerOldVersion != '') {
                                echo 'remove successfully'
                            }
                        } else
                            echo 'khong ton tai';
                    } catch (Exception ex) {
                        echo ex;
                    }

                }

                sh('docker run  --name oderFx -d -p 8090:8090 oderFx')
            }
        }

    }
}
