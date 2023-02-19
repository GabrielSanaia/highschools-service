package com.example.highschoolsservice;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/highSchools")
public class HighSchoolsController {

    @GetMapping("/getHighSchools")
    public ArrayList<String> getHighSchools(){
        return new ArrayList<String>(
                Arrays.asList("HighSchool1", "HighSchool2", "HighSchool3"));
    }
    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/getOurPrograms")
    public ArrayList<String> getourPrograms(){

    List<ServiceInstance> instances = discoveryClient.getInstances("programs-service");
    ServiceInstance serviceInstance = instances.get(0);

    String baseURL = serviceInstance.getUri().toString();
    baseURL += "/programs/getPrograms";
    ArrayList<String> arr = restTemplate.getForObject(baseURL, ArrayList.class);

    arr.remove(arr.size() - 1);

    return arr;

    }
}
