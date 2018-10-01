node {
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
}

//pipeline {
  //  agent {   
    //    script {
      //      docker.withServer('tcp://10.0.3.134:2375')
       //}
        //docker {
          //  image 'maven:3-alpine'
            //args '-v /root/.m2:/root/.m2'
        //}
    //}
    //stages {
      //  stage('Build') {
        //    steps{
          //      sh 'mvn -B -DskipTests clean package'
           // }
        //}
        //stage('Test') {
          //  steps {
             //   sh 'mvn test'
           // }
            //post {
              //  always {
                //    junit 'target/surefire-reports/*.xml'
                //}
            //}
        //}
        //stage('Deliver') { 
          //  steps {
            //    sh './jenkins/scripts/deliver.sh' 
            //}
        //}
    //}
//}


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
