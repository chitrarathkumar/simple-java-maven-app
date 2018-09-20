node {
        docker.withServer('tcp://10.0.3.87:2376') {
                stages {
                        stage('Build') {
                                sh 'docker run maven:3-alpine'
                                sh 'mvn -B -DskipTests clean package'
                        }
                        stage('Build') {
                                sh 'docker run hello-world'
                        }
                }
        }
}
