package com.javaweb.api;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javaweb.Model.BuildingDTO;
import com.javaweb.service.BuildingService;

@RestController
public class BuildingAPI {
	@Autowired
	private BuildingService buildingService;

	@GetMapping(value = "/api/building/")
	public List<BuildingDTO> getBuilding(@RequestParam Map<String, Object> params,
			@RequestParam(name="typeCode", required = false) List<String> typeCode) {
		List<BuildingDTO> result = buildingService.findAll(params, typeCode);
		return result;
	}
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

//@RestController
//public class BuildingAPI {
//
//	@GetMapping(value = "/api/building/")
//	public List<BuildingDTO> getBuilding(@RequestParam(value = "name", required = false) String nameBuilding,
//			@RequestParam(value = "numberOfBasement", required = false) Integer numberOfBasement,
//			@RequestParam(value = "ward", required = false) String ward) {
////         xu ly duoi DB xong roi
//		List<BuildingDTO> listBuildings = new ArrayList<>();
//		BuildingDTO buildingDTO1 = new BuildingDTO();
//		buildingDTO1.setName("ABC Building");
//		buildingDTO1.setNumberOfBasement(3);
//		buildingDTO1.setWard("Tan Mai");
//		BuildingDTO buildingDTO2 = new BuildingDTO();
//		buildingDTO2.setName("ACM Tower");
//		buildingDTO2.setNumberOfBasement(2);
//		buildingDTO2.setWard("Da Cao");
//		listBuildings.add(buildingDTO1);
//		listBuildings.add(buildingDTO2);
//		return listBuildings;
//	}
//
//	public Object getBuilding(@RequestBody BuildingDTO building) {
////    	xu ly duoi DB xong roi
//		try {
//			valiDate(building);
//		} catch (FieldRequiredException e) {
//			ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
//			errorResponseDTO.setError(e.getMessage());
//			List<String> details = new ArrayList<>();
//			details.add("Check name or numberofbasement again cause it is null!");
//			errorResponseDTO.setDetail(details);
//			return errorResponseDTO;
//		}
//		System.out.print(5 / 0);
////    	valiDate.building;
//		return null;
//		BuildingDTO result = new BuildingDTO();
//		result.setName(nameBuilding);
//		result.setNumberOfBasement(numberOfBasement);
//		result.setWard(ward);
//		return (List<BuildingDTO>) result;
//	}
//
//	public void valiDate(BuildingDTO buildingDTO) throws FieldRequiredException {
//		if (buildingDTO.getName() == null || buildingDTO.getName().equals("")
//				|| buildingDTO.getNumberOfBasement() == null) {
//			throw new FieldRequiredException("name or nameofnumberofbasement is null");
//		}
//	}
//
//	@RequestMapping(value = "/api/building/", method = RequestMethod.POST)
//	public BuildingDTO getBuilding2(@RequestBody BuildingDTO building) {
//		return building;
//	}
//
//	@DeleteMapping(value = "/api/building/{id}/{name}")
//	public void deleteBuilding(@PathVariable Integer id, @PathVariable String name) {
//		System.out.print("Deleted building has id " + id);
//	}
//}