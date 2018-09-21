node {
        docker.withServer('tcp://10.0.3.87:2376').image('maven:3-alpine').inside {
                stage('Build1') {
                        sh 'mvn -B -DskipTests clean package'
                }
                stage('Build2') {
                        sh 'docker run hello-world'
                }
        }
}
