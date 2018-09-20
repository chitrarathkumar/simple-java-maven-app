node {
        docker.withServer('tcp://10.0.3.87:2376') {
                stage('Build1') {
                        sh 'docker run maven:alpine'
                        sh 'mvn -B -DskipTests clean package'
                }
                stage('Build2') {
                        sh 'docker run hello-world'
                }
        }
}
