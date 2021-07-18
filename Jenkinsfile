pipeline {
    agent any

    stages {
        stage('build') {
            steps {
                sh('mvn clean install -DskipTests=false')
            }
        }
        stage('add_Permission') {
            steps {
                sh('chmod -R 777 *')
            }
        }
        stage('build_docker') {
            steps {
                sh('docker build -t demo_jenkins .')
            }
        }

        stage('docker_run') {

            steps {
                script {

                    try {
                        String imageExists = sh(script: 'docker ps --filter "name=demo_jenkins"', returnStdout: true)
                        echo imageExists;
                        if (imageExists != null && imageExists.contains('demo_jenkins')) {
                            echo 'Ton tai container old version';
//                            String isStop = sh(script: 'docker stop demo_jenkins"', returnStdout: true)
//                            if (isStop != null && isStop != '') {
//                                echo 'stop successfully';
//                            }
                            String isRemoveContainerOldVersion = sh(script: 'docker rm --force $(docker ps --filter name=demo_jenkins -q)', returnStdout: true)
                            if (isRemoveContainerOldVersion != null && isRemoveContainerOldVersion != '') {
                                echo 'remove successfully'
                            }
                        } else
                            echo 'khong ton tai';
                    } catch (Exception ex) {
                        echo ex;
                    }

                }

                sh('docker run  --name demo_jenkins -d -p 8090:8090 demo_jenkins')
            }
        }

    }
}
