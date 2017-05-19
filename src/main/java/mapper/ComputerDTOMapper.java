package mapper;

import java.util.ArrayList;
import java.util.List;

import dto.ComputerDTO;
import dto.ComputerDTO.ComputerDTOBuilder;
import model.CompanyEntity;
import model.ComputerEntity;
import model.ComputerEntity.ComputerBuilder;

public class ComputerDTOMapper {

	/**
	 * create ComputerDTO corresponding to computerEntity.
	 * @param computer entity.
	 * @return ComputerDTO map with parameter computer
	 */
	public static ComputerDTO createComputerDTO(ComputerEntity computer) {

		ComputerDTOBuilder computerDTOBuilder = new ComputerDTOBuilder();

		if (computer != null) {
			computerDTOBuilder.id(computer.getId()).name(computer.getName());

			if (computer.getIntroduced() != null) {
				computerDTOBuilder.introduced(computer.getIntroduced().toString());
			}
			if (computer.getDiscontinued() != null) {
				computerDTOBuilder.discontinued(computer.getDiscontinued().toString());
			}
			if (computer.getCompanyEntity() != null) {

				computerDTOBuilder.companyId(computer.getCompanyEntity().getId());
				computerDTOBuilder.companyName(computer.getCompanyEntity().getName());
			}
		}

		return computerDTOBuilder.build();

	}

	/**
	 * create computer from computerDTO.
	 * @param computerDTO .
	 * @return Computer
	 */

	public static ComputerEntity createComputer(ComputerDTO computerDTO) {

		ComputerBuilder computerBuilder = ComputerEntity.computerBuilder();

		computerBuilder.id(computerDTO.getId()).name(computerDTO.getName());
		computerBuilder.introduced(DataMapper.convertStringToDate(computerDTO.getIntroduced()));
		computerBuilder.discontinued(DataMapper.convertStringToDate(computerDTO.getDiscontinued()));
		
		if (computerDTO.getCompanyName() != null && computerDTO.getCompanyId() > 0) {
			
			computerBuilder.company(new CompanyEntity(computerDTO.getCompanyId(), computerDTO.getCompanyName()));
		}

		return computerBuilder.build();

	}

	/**
	 * create computerDTO list from computer list.
	 * @param computerList .
	 * @return ComputerDTO List
	 */
	public static List<ComputerDTO> createComputerDTOList(List<ComputerEntity> computerList) {
		ArrayList<ComputerDTO> computerDTOList = new ArrayList<ComputerDTO>();

		for (ComputerEntity computer : computerList) {
			computerDTOList.add(createComputerDTO(computer));
		}

		return computerDTOList;

	}
}