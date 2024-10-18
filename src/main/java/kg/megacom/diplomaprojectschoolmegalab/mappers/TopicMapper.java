package kg.megacom.diplomaprojectschoolmegalab.mappers;

import kg.megacom.diplomaprojectschoolmegalab.dto.TopicDto;
import kg.megacom.diplomaprojectschoolmegalab.entity.Topic;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TopicMapper {

    // Method to map a single Topic entity to TopicDto
    public TopicDto toTopicDto(Topic topic) {
        return new TopicDto(topic.getId(), topic.getTitle(), topic.getDescription());
    }

    // Method to map a list of Topic entities to a list of TopicDtos
    public List<TopicDto> toTopicDtoList(List<Topic> topics) {
        return topics.stream()
                .map(this::toTopicDto)  // Use the toTopicDto method for each Topic in the list
                .collect(Collectors.toList());
    }
}
