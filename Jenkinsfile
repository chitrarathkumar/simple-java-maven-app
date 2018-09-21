node {
    /* Requires the Docker Pipeline plugin to be installed */
    docker.withServer('tcp://10.0.3.87:2376').image('maven:3-alpine').inside('-v $HOME/.m2:/root/.m2') {
        stage('Build') {
            sh 'mvn --version'
        }
    }
}
