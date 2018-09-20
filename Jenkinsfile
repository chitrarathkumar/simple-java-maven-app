node {
        docker.withServer('tcp://10.0.3.87:2376', 'swarm-certs') {
                stage('Build') {
                        sh 'docker run hello-world'
                }
        }
}
