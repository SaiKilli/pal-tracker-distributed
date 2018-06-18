package io.pivotal.pal.tracker.allocations;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestOperations;

public class ProjectClient {

    private final Logger logger = LoggerFactory.getLogger(getClass());
    private ProjectCache projectCache;
    private final RestOperations restOperations;
    private final String registrationServerEndpoint;

    public ProjectClient(ProjectCache projectCache, RestOperations restOperations, String registrationServerEndpoint) {

        this.projectCache = projectCache;
        this.restOperations= restOperations;
        this.registrationServerEndpoint = registrationServerEndpoint;
    }

    @HystrixCommand(fallbackMethod = "getProjectFromCache")
    public ProjectInfo getProject(long projectId) {
        ProjectInfo projectInfo = restOperations.getForObject(registrationServerEndpoint + "/projects/" + projectId, ProjectInfo.class);
        projectCache.cacheProjectInfo(projectId, projectInfo);
        return projectInfo;
    }

    public ProjectInfo getProjectFromCache(long projectId){
        logger.info("Getting project with id {} from cache", projectId);
        return projectCache.getFromCache(projectId);
    }
}
