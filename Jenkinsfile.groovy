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





node {
    docker.withServer('tcp://10.0.3.134:2375'){
        stages {
            stage('Non-Parallel Stage') {
                echo 'This stage will be executed first.'
            }
            stage('Parallel Stage') {
                when {
                    branch 'master'
                }
                failFast true
                parallel {
                    stage('Branch A') {
                        label "for-branch-a"
                        echo "On Branch A"
                    }
                    stage('Branch B') {
                        label "for-branch-b"
                        echo "On Branch B"
                    stage('Branch C') {
                            label "for-branch-c"
                        stages {
                            stage('Nested 1') {
                                echo "In stage Nested 1 within Branch C"
                            }
                            stage('Nested 2') {
                                echo "In stage Nested 2 within Branch C"
                            }
                        }
                    }
                }
            }
        }
    }
}
