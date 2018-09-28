node {
    docker.withServer('tcp://10.0.3.134:2375'){
        stage('Back-end') {
    /* Requires the Docker Pipeline plugin to be installed */
    docker.image('maven:3-alpine').inside('-v $HOME/.m2:/root/.m2') {
                sh 'mvn -B'
            }
        }
        stage('Front-end') {
            docker.image('node:7-alpine').inside {
                sh 'node --version'
            }
        }
    }
}
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
