/**
 * Global variables
 */
def varBranchName = 'refs/heads/master'
def cleanScript = 'mvn clean           --fail-at-end'
def buildScript = 'mvn clean install   --fail-at-end'
def packageScript = 'mvn clean package   --fail-at-end'
def packageOnlyScript = 'mvn package         --fail-at-end'
def deployScript = 'mvn clean deploy    --fail-at-end'
def propsFile = null
def props = new Properties()
def scmDetails = null
//def argumentForDocker = ' -v /home/apps/Volante_600:/home/apps/Volante_600:rw,z '
def isDeployPhase=false

/**
 * Creates a build information from jenkins build as a table rows.
 * @return buildInfo
 */
def generateBuildInfo() {
    def buildInfo = ""
    try {
        buildInfo +=
                "<tr>\n<td>JOB_URL</td>\n<td>${env.JOB_URL}</td>\n</tr>\n" +
                        "<tr>\n<td>BUILD_NUMBER</td>\n<td>${env.BUILD_NUMBER}</td>\n</tr>\n" +
                        "<tr>\n<td>BUILD_ID</td>\n<td>${env.BUILD_ID}</td>\n</tr>\n" +
                        "<tr>\n<td>BUILD_DISPLAY_NAME</td>\n<td>${env.BUILD_DISPLAY_NAME}</td>\n</tr>\n" +
                        "<tr>\n<td>JOB_NAME</td>\n<td>${env.JOB_NAME}</td>\n</tr>\n" +
                        "<tr>\n<td>BUILD_TAG</td>\n<td>${env.BUILD_TAG}</td>\n</tr>\n" +
                        "<tr>\n<td>EXECUTOR_NUMBER</td>\n<td>${env.EXECUTOR_NUMBER}</td>\n</tr>\n" +
                        ""
    } catch (Exception ex) {
        echo ex
    }

    return buildInfo
}

//def receipientList = [
//        [
//                $class: 'CulpritsRecipientProvider'
//        ],
//        [
//                $class: 'RequesterRecipientProvider'
//        ],
//        [
//                $class: 'DevelopersRecipientProvider'
//        ],
//        [
//                $class: 'FailingTestSuspectsRecipientProvider'
//        ],
//        [
//                $class: 'FirstFailingBuildSuspectsRecipientProvider'
//        ],
//        [
//                $class: 'UpstreamComitterRecipientProvider'
//        ]
//]

//def stakeHolders = "aldrine@volantetech.com, umanath.kuppuswamy@volantetech.com, prabhu@volantetech.net, arunprasath@volantetech.net"

/**
 * Properties for Jenkins Build
 */

//properties(
//        [
//                buildDiscarder(
//                        logRotator(artifactDaysToKeepStr: '',
//                                artifactNumToKeepStr: '',
//                                daysToKeepStr: '100',
//                                numToKeepStr: '100'
//                        )
//                ),
//                disableConcurrentBuilds(),
//                durabilityHint('MAX_SURVIVABILITY'),
//                disableResume(),
//                [
//                        $class      : 'CopyArtifactPermissionProperty',
//                        projectNames: '*'
//                ],
//                parameters(
//                        [
//                                booleanParam(
//                                        defaultValue: true,
//                                        description: 'Do u want to use mounted maven?',
//                                        name: 'MOUNT_MAVEN'
//                                ),
//                                string(
//                                        defaultValue: 'N/A',
//                                        description: 'To build a branch',
//                                        name: 'BUILD_BRANCH',
//                                        trim: true
//                                ),
//                                string(
//                                        defaultValue: 'N/A',
//                                        description: 'To build a tag',
//                                        name: 'USER_BUILD_TAG',
//                                        trim: true
//                                ),
//                                booleanParam(
//                                        defaultValue: true,
//                                        description: 'Need debug information from Maven',
//                                        name: 'MVN_DEBUG'
//                                ),
//                                booleanParam(
//                                        defaultValue: false,
//                                        description: 'Do u want to deploy the build into Nexus?',
//                                        name: 'NEXUS_DEPLOY'
//                                ),
//                                booleanParam(
//                                        defaultValue: true,
//                                        description: 'Do u want to use docker designer (with runtime license)?',
//                                        name: 'USE_DOCKER_DESIGNER'
//                                )
//                        ]
//                )
//        ])

/**
 * Build Configuration / Scripts
 */
node {
    if (env.gitlabActionType == "PUSH" || env.gitlabActionType == "MERGE") {
        currentBuild.displayName = "$BUILD_ID (WIP)"
        if (env.gitlabActionType == "MERGE")
            currentBuild.description = "Initiated by Merge. This is a Work In Progress build, please do-not consume for QA process"
        if (env.gitlabActionType == "PUSH")
            currentBuild.description = "Initiated by $env.gitlabUserName. This is a Work In Progress build, please do-not consume for QA process"
    }
    echo "env.MOUNT_MAVEN${env.MOUNT_MAVEN} ${env.MOUNT_MAVEN.getClass().getName()}"
    echo "env.NEXUS_DEPLOY${env.NEXUS_DEPLOY}"


//    if (env.USER_BUILD_TAG != "N/A")
//        varBranchName = "refs/tags/${env.USER_BUILD_TAG}"
//    else if (env.BUILD_BRANCH != "N/A")
//        varBranchName = "refs/heads/${env.BUILD_BRANCH}"
//    else if (env.gitlabSourceBranch != null && env.gitlabSourceBranch != "null")
//        varBranchName = "refs/heads/${env.gitlabSourceBranch}"
//    else
//        varBranchName = "refs/heads/master"
//    if (env.NEXUS_DEPLOY.toBoolean())
//        isDeployPhase=true
//    if (env.USE_DOCKER_DESIGNER.toBoolean()) {
//        argumentForDocker = '';
//    }
//    echo "Before exception"
//    try {
//        if (env.MOUNT_MAVEN.toBoolean()) {
//            argumentForDocker = "${argumentForDocker} -v /SSD1/Jenkins/.m2:/usr/share/.m2_old:rw,z"
//        }
//    } catch (java.lang.NullPointerException e) {
//        echo e;
//        argumentForDocker = "${argumentForDocker} -v /SSD1/Jenkins/.m2:/usr/share/.m2_old:rw,z"
//    }
//    if (!env.MVN_DEBUG.toBoolean()) {
//        buildScript = "${buildScript} "
//        cleanScript = "${cleanScript} "
//        packageScript = "${packageScript} "
//        packageOnlyScript = "${packageOnlyScript} "
//    } else {
//        buildScript = "${buildScript}          -X -Dorg.slf4j.simpleLogger.log.volante.runtime=trace"
//        cleanScript = "${cleanScript}          -X -Dorg.slf4j.simpleLogger.log.volante.runtime=trace"
//        packageScript = "${packageScript}        -X -Dorg.slf4j.simpleLogger.log.volante.runtime=trace"
//        packageOnlyScript = "${packageOnlyScript}    -X -Dorg.slf4j.simpleLogger.log.volante.runtime=trace"
//    }
    //echo argumentForDocker
    sh 'docker-compose up --build -d'
    sh 'sleep 30'
    cleanWs()
}
pipeline {
    environment {
        projectDir = '.'
    }
    agent {
        docker {
            image "maven-jdk-8-docker-image:98"
            args "-v /SSD1/Jenkins/.m2:/usr/share/.m2_old:rw,z --net docker-net-services"
        }
    }
    options {
        timeout(time: 1, unit: 'HOURS')
    }
    stages {
        stage('build') {
            steps {
                retry(0) {
                    timeout(time: 30, unit: 'MINUTES') {

//                        script {
//                            sh 'rsync -ru /usr/share/.m2_old/repository/ /usr/share/.m2/repository/ --exclude repository/com --exclude com/volantetech --perms --chmod=777'
//                        }
//
//                        //For installing build utils
//                        sh 'mkdir -p build'
//                        copyArtifacts filter: 'target/BuildUtilsSDK.zip', fingerprintArtifacts: true, flatten: true, projectName: 'Build-Utils', selector: latestSavedBuild(), target: '/tmp'
//                        fileOperations([fileUnZipOperation(filePath: '/tmp/BuildUtilsSDK.zip', targetLocation: '.')])
//                        sh 'chmod -R 777 BuildUtilsSDK'
//                        dir('BuildUtilsSDK') {
//                            sh './install.sh'
//                        }

//                        dir('build') {

                        script {
                            scmDetails = checkout(
                                    [
                                            $class                           : 'GitSCM',
                                            branches                         : [
                                                    [
                                                            name: varBranchName
                                                    ]
                                            ],
                                            doGenerateSubmoduleConfigurations: false,
                                            extensions                       :
                                                    []
                                            ,
                                            submoduleCfg                     : [],
                                            userRemoteConfigs                : [
                                                    [
                                                            credentialsId: 'chitrarathkumar-github.com',
                                                            url          : 'https://github.com/chitrarathkumar/simple-java-maven-app.git'
                                                    ]
                                            ]
                                    ]
                            )
                            sh 'ls -l'

//                            propsFile = readFile("build.properties")
//                            props.load(new StringReader(propsFile))
                        }

//                        withEnv(
//                                [
//                                        'VOLANTE_HOME=/home/apps/Volante_600'
//                                ]
//                        ) {
//
//                            sh buildScript
//
//                        }
//                        dir('target') {
//                            sh "mkdir VolBaseSDK"
//                        }
//                        dir('tools/catridge-read-only') {
//                            withEnv(
//                                    [
//                                            'VOLANTE_HOME=/home/apps/Volante_600'
//                                    ]
//                            ) {
//                                sh "./catridge-read-only.sh"
//                            }
//                        }
//                        dir('tools/deploy-tools') {
//                            sh "mvn package exec:java"
//                        }
//                        dir('data-structure') {
//                            sh "${cleanScript}"
//                        }
//
//                        fileOperations(
//                                [
//                                        folderCopyOperation(
//                                                destinationFolderPath: 'target/VolBaseSDK',
//                                                sourceFolderPath: "tools/sdk-installer"
//                                        ),
//                                        folderCopyOperation(
//                                                destinationFolderPath: 'target/VolBaseSDK/tools',
//                                                sourceFolderPath: "tools/designer-addins"
//                                        ),
//                                        folderCopyOperation(
//                                                destinationFolderPath: 'target/VolBaseSDK/data-structure',
//                                                sourceFolderPath: "data-structure"
//                                        ),
//                                        folderCopyOperation(
//                                                destinationFolderPath: 'target/VolBaseSDK/sample-impl',
//                                                sourceFolderPath: "sample-impl"
//                                        ),
//                                        fileCopyOperation(
//                                                excludes: '',
//                                                flattenFiles: true,
//                                                includes: 'framework/pom.xml',
//                                                targetLocation: 'target/VolBaseSDK'
//                                        ), fileCopyOperation(
//                                        excludes: '',
//                                        flattenFiles: true,
//                                        includes: 'build.properties',
//                                        targetLocation: 'target/VolBaseSDK'
//                                ),
//                                        fileZipOperation("target/VolBaseSDK")
//                                ]
//                        )
//
//                        dir("target/VolBaseSDK") {
//                            withEnv(
//                                    [
//                                            'VOLANTE_HOME=/home/apps/Volante_600',
//                                    ]
//                            ) {
//                                dir("sample-impl") {
//                                    sh "${packageScript}"
//                                }
//
//                            }
//                        }
//                        }
                    }
                }
            }

//            post {
//                always {
//                    withEnv(
//                            [
//                                    'VOLANTE_HOME=/home/apps/Volante_600'
//                            ]
//                    ) {
//                        dir("merge-report") {
//                            sh "${packageOnlyScript}"
//                        }
//                    }
//
//                    archiveArtifacts allowEmptyArchive: true,
//                            artifacts: 'VolBaseSDK.zip,**/MergedTestReport/**/*,**/test-suites/**/*,**/target/surefire-reports/TEST*.xml,**/all-tests.xml'
//                    junit allowEmptyResults: true,
//                            healthScaleFactor: 0.5,
//                            testResults: '**/target/surefire-reports/TEST*.xml,**/all-tests.xml'
//                    //For log analysis
//                    step([$class: 'LogParserPublisher', parsingRulesPath: '/etc/logParserJenkins/simple.txt', useProjectRule: false])
//
//                }
//
//                failure {
//                    emailext attachLog: true,
//                            body: "<p>Build details: </br>" +
//                                    " <table style=\"width:100%\">\n" +
//                                    "  <tr>\n" +
//                                    "    <th>Items</th>\n" +
//                                    "    <th>Details</th>\n" +
//                                    "  </tr>\n" +
//                                    generateBuildInfo() +
//                                    "</table> " +
//                                    "</p>",
//                            compressLog: true,
//                            mimeType: 'text/html',
//                            recipientProviders: receipientList,
//                            replyTo: 'aldrine@volantetech.com',
//                            subject: "${env.JOB_NAME} - Build # [${env.BUILD_NUMBER}] : ${currentBuild.currentResult}",
//                            from: 'jenkins@volantetech.net',
//                            to: 'jenkins@volantetech.net'
//                }
//
//                success {
//                    build job: 'VolBase-QA',
//                            parameters: [
//                                    string(name: 'BUILD_BRANCH', value: 'N/A'),
//                                    string(name: 'USER_BUILD_TAG', value: env.USER_BUILD_TAG),
//                                    booleanParam(name: 'MVN_DEBUG', value: true),
//                                    booleanParam(name: 'UPSTREAM_TRIGGERED', value: true),
//                                    string(name: 'VOLBASE_VERSION', value: props.volbase_version)
//                            ],
//                            wait: false
//                }
//                regression {
//                    emailext attachLog: true,
//                            body: "<p>Build has failed with regression! Please verify the logs for more details!" +
//                                    "Build details: </br>" +
//                                    " <table style=\"width:100%\">\n" +
//                                    "  <tr>\n" +
//                                    "    <th>Items</th>\n" +
//                                    "    <th>Details</th>\n" +
//                                    "  </tr>\n" +
//                                    generateBuildInfo() +
//                                    "</table> " +
//                                    "</p>",
//                            compressLog: true,
//                            mimeType: 'text/html',
//                            recipientProviders: receipientList,
//                            replyTo: 'aldrine@volantetech.com',
//                            subject: "Regression! ${env.JOB_NAME} - Build # [${env.BUILD_NUMBER}] : ${currentBuild.currentResult}",
//                            from: 'jenkins@volantetech.net',
//                            to: "${stakeHolders}"
//                }
//            }
//        }
//        stage('test') {
//            steps{
//                dir('sample-impl/rest-sample') {
//                    sh 'build_rest.sh'
//                }
//                fileOperations(
//                        [
//                                fileCopyOperation(
//                                        excludes: '',
//                                        flattenFiles: true,
//                                        includes: 'sample-impl/generated-war/volpayrest.war',
//                                        targetLocation: 'docker/tomcat/apache-tomcat-8.5.20/webapps'
//                                )
//                        ]
//                )
//                dir('docker') {
//                    sh 'docker-compose up --build -d'
//                    sh 'wait-for-it.sh localhost:8080 --strict \
//                        sleep 40 \
//                        echo waited 40s'
//                }
//                dir('framework') {
//                    sh '/home/apps/Volante_600/tools/test.sh -excludeTags dbconfig -reportdir testreport -reportformat xml -reportoption merged rest-services/rest-services.vpj'
//                }
//                dir('docker') {
//                    sh 'docker-compose down'
//                }
//            }
//            post{
//                always{
//                    archiveArtifacts allowEmptyArchive: true,
//                            artifacts: '**/framework/all-tests.xml'
//                }
//            }
//        }
        }
    }
}
node{
    sh 'docker-compose down'
}