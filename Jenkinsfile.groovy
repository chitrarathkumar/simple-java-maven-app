/*node {
    docker.withServer('tcp://10.0.3.134:2375'){
        docker.image('maven:3-alpine').inside('-v $HOME/.m2:/root/.m2') {
            stage('Build') {
                sh 'mvn -B -DskipTests clean package'
            }
            stage('Test') {
                sh 'mvn test'
                //post {
                    //always {
                junit 'target/surefire-reports/*.xml'
                  //  }
                //}
            }
            stage('Deliver') {
                sh './jenkins/scripts/deliver.sh' 
            }
        }
    }
}*/

/*pipeline {
    agent none
    stages {
        stage('Build test and deliver') {
            steps{
                script {
                    docker.withServer('tcp://10.0.3.134:2375'){
                        docker.image('maven:3-alpine').inside('-v $HOME/.m2:/root/.m2') {
                            stage('Build'){
                                sh 'mvn -B -DskipTests clean package'
                            }
                            stage('Test'){
                                sh 'mvn test'
                                junit 'target/surefire-reports/*.xml'
                            }
                            stage('Deliver') { 
                                sh './jenkins/scripts/deliver.sh' 
                            }
                        }
                    }
                }
            }
        }
    }
}*/


/*pipeline {
    agent none
    stages {
        stage('Back-end') {
            agent {
                docker.withServer('tcp://10.0.3.134:2375') { image 'maven:3-alpine' }
            }
            steps {
                sh 'mvn --version'
            }
        }
        stage('Front-end') {
            agent {
                docker { image 'node:7-alpine' }
            }
            steps {
                sh 'node --version'
            }
        }
    }
}*/





/*node {
    docker.withServer('tcp://10.0.3.134:2375'){
        docker.image('maven:3-alpine').inside('-v $HOME/.m2:/root/.m2') {
            stage('Build') {
                sh 'mvn -B -DskipTests clean package'
            }
            stage('Test') {
                parallel dockerA: {
                    node('master') {
                        echo 'first one'
                        sh 'docker --version'
                    }
                },
                dockerB: {
                    node('master') {
                        echo 'second one'
                        sh 'docker --version'
                    }
                }
            }
        }
    }
}*/

pipeline {
    agent docker
    stages {
        stage('Non-Parallel Stage') {
            steps {
                echo 'This stage will be executed first.'
            }
        }
        stage('Parallel Stage') {
           // when {
             //   branch 'master'
            //}
            failFast true
            parallel {
                stage('Branch A') {
                    agent {
                        label "master"
                    }
                    steps {
                        echo "On Branch A"
                    }
                }
                stage('Branch B') {
                    agent {
                        label "master"
                    }
                    steps {
                        echo "On Branch B"
                    }
                }
                stage('Branch C') {
                    agent {
                        label "master"
                    }
                    stages {
                        stage('Nested 1') {
                            steps {
                                echo "In stage Nested 1 within Branch C"
                            }
                        }
                        stage('Nested 2') {
                            steps {
                                echo "In stage Nested 2 within Branch C"
                            }
                        }
                    }
                }
            }
        }
    }
}
