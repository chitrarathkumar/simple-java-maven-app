node {
        docker.withServer('tcp://10.0.3.87:2376'){
    /* Requires the Docker Pipeline plugin to be installed */
            docker.image('maven:3-alpine').inside('-v $HOME/.m2:/root/.m2') {
                stage('Build') {
                    sh 'mvn -B -DskipTests clean package'
                 }
            }
        }
}
