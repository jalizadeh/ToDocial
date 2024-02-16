pipeline {
    agent any
    
    parameters {
        choice( name: 'BRANCH',
            choices: ['test', 'master'],
            description: 'Select branch to run tests')
    }
    
    stages {
        stage ('Fetch repo') {
            steps {
                script {
                    // Get the current build number
                    def buildNumber = currentBuild.number
                    // Get the current branch name
                    def branchName = params.BRANCH
                    // Set the custom display name
                    currentBuild.displayName = "#${buildNumber} - ${branchName}"
                    
                    // Construct the dynamic stage title
                    // def dynamicTitle = "Stage for branch ${branchName} - Build ${buildNumber}"
                    // currentBuild.displayName = dynamicTitle
                }
                
                git branch: '${BRANCH}', url: 'file:///D:/Java%20Projects/ToDocial-Social-Todo-Manager'
                
            }
        }
        
        stage ('Install'){
            steps {
                echo "Installing dependencies"
            }
        }
        
        stage ('Lint') {
            steps {
                echo "Linting..."
            }
        }
        
        stage ('Test') {
            steps {
                //echo "hi"
                bat "mvn clean test"
                //bat "mvn clean test -Dtest=GymUtilsTest"
            }
        }
        
        stage ('Deployment') {
            when {
                expression { BRANCH == 'master'}
            }
            steps {
                input message: 'Really deploy on master', ok: 'Yes'
                echo "deploying on ${BRANCH}"
            }
        }
    }
    
    post {
        
        
        always {
            // Display Surefire test results
            junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
            //junit allowEmptyResults: true, testResults: '**/pylint_junit.xml'
            
            jacoco(
                execPattern: 'target/jacoco.exec',
                classPattern: 'target/classes/com/jalizadeh/todocial',
                sourcePattern: 'src/main'
            )
        }
    
    }
}