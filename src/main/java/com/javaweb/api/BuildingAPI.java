package com.javaweb.api;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javaweb.Model.BuildingDTO;
import com.javaweb.service.BuildingService;

@RestController
@PropertySource("classpath:application.properties")
public class BuildingAPI {

    @Autowired
    private BuildingService buildingService;

    @Value("${dev.java}")
    private String data;

    @GetMapping(value="/api/building/")
    public List<BuildingDTO> getBuilding(@RequestParam Map<String,Object> params,
                                         @RequestParam(name="typeCode", required = false) List<String> typeCode) {
        List<BuildingDTO> result = buildingService.findAll(params,typeCode);
        return result;
    }



//@RestController
//public class BuildingAPI {
//    @GetMapping(value="/api/building/")
//    public BuildingDTO getBuilding(@RequestParam(value="name", required = false) String nameBuilding,
//    						@RequestParam(value="numberOfBasement", required = false) Integer numberOfBasement,
//							@RequestParam(value="ward", required = false) String ward) {
//			BuildingDTO result = new BuildingDTO();
//			result.setName(nameBuilding);
//			result.setNumberOfBasement(numberOfBasement);
//			return result;
//	}


	@DeleteMapping(value = "/api/building/{id}")
	public void deleteBuilding(@PathVariable Integer id) {
		System.out.print(data);
	}
}