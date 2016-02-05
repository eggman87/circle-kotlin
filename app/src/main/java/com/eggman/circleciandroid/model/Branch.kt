package com.eggman.circleciandroid.model

/**
 * This class is responsible for x.
 * @author Created by matt harris.
 * @since 2/5/16
 */
data class Branch(val pusherLogins:List<String>, val lastNonSuccess:Build, val lastSuccess:Build, val recentBuilds:List<Build>){
}