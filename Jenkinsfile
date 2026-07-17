pipeline {
    agent any

    tools {
        maven 'Maven-3.9'
        jdk 'JDK-11'
    }

    parameters {
        choice(name: 'BROWSER', choices: ['chrome', 'firefox', 'edge'], description: 'Select browser for test execution')
        booleanParam(name: 'HEADLESS', defaultValue: true, description: 'Run tests in headless mode')
        string(name: 'TEST_SUITE', defaultValue: 'testng.xml', description: 'TestNG suite XML file')
    }

    environment {
        BROWSER = "${params.BROWSER}"
        HEADLESS = "${params.HEADLESS}"
    }

    options {
        timestamps()
        timeout(time: 30, unit: 'MINUTES')
        buildDiscarder(logRotator(numToKeepStr: '10'))
    }

    stages {
        stage('Checkout') {
            steps {
                echo '=== Checking out source code ==='
                checkout scm
            }
        }

        stage('Build') {
            steps {
                echo '=== Compiling the project ==='
                bat 'mvn clean compile -DskipTests'
            }
        }

        stage('Test') {
            steps {
                echo "=== Running tests on ${params.BROWSER} browser (headless: ${params.HEADLESS}) ==="
                bat "mvn test -Dbrowser=${params.BROWSER} -Dheadless=${params.HEADLESS} -DsuiteXmlFile=${params.TEST_SUITE}"
            }
        }

        stage('Report') {
            steps {
                echo '=== Publishing test reports ==='

                // Publish TestNG results
                testNG(
                    reportFilenamePattern: '**/testng-results.xml',
                    failureOnFailedTestConfig: true
                )

                // Publish ExtentReports HTML
                publishHTML(target: [
                    allowMissing: false,
                    alwaysLinkToLastBuild: true,
                    keepAll: true,
                    reportDir: 'reports',
                    reportFiles: '*.html',
                    reportName: 'Extent Test Report'
                ])
            }
        }
    }

    post {
        always {
            echo '=== Cleaning up workspace ==='
            archiveArtifacts artifacts: 'reports/**/*', allowEmptyArchive: true
            archiveArtifacts artifacts: '**/surefire-reports/**/*', allowEmptyArchive: true
        }
        success {
            echo '=== Build completed SUCCESSFULLY ==='
        }
        failure {
            echo '=== Build FAILED! Check reports for details ==='
        }
        unstable {
            echo '=== Build is UNSTABLE! Some tests may have failed ==='
        }
    }
}
