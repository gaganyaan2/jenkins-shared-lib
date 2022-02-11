def call(Map pipelineParams){
pipeline
{
    agent {
        label 'lp-worker-1'
    }
    options{
        timestamps()
        timeout(time: 30, unit: 'MINUTES')
        buildDiscarder(logRotator(numToKeepStr: '10'))
    }
    environment {
        scannerHome = tool 'sonarqube'
    }
    stages
    {
        stage('git')
        {
            steps{
                GitCheckout(pipelineParams.repo_url)
            }
            
        }
        stage('sonarqube')
        {
            steps{
                SonarQubeStaticAnalysis()
            }
        }
    }
}
return this
}