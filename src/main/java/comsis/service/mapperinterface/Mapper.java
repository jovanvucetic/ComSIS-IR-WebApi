package comsis.service.mapperinterface;

public interface Mapper<DtoModel, ServiceModel>{

    DtoModel toDto(ServiceModel serviceModel);

    ServiceModel toServiceModel(DtoModel dtoModel);
}
