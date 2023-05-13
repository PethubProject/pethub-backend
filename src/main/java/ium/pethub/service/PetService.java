package ium.pethub.service;

import ium.pethub.domain.entity.Pet;
import ium.pethub.domain.entity.User;
import ium.pethub.domain.repository.PetRepository;
import ium.pethub.domain.repository.UserRepository;
import ium.pethub.dto.pet.request.PetRequestDto;
import ium.pethub.dto.pet.response.PetInfoResponseDto;
import ium.pethub.dto.pet.response.PetListResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PetService {
    private final PetRepository petRepository;
    private final UserRepository userRepository;

    public void registerPet(Long ownerId, PetRequestDto requestDto) {
        User user = userRepository.findById(ownerId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. id=" + ownerId));

        Pet pet = requestDto.toEntity(user);
        petRepository.save(pet);
    }

    public PetInfoResponseDto findPetByPetId(Long petId) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new IllegalArgumentException("해당 펫이 없습니다. id=" + petId));

        return new PetInfoResponseDto(pet);
    }

    public List<PetListResponseDto> findPetListByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. id=" + userId));

        List<Pet> petList = petRepository.findAllByUser(user);

        return petList.stream()
                .map(pet -> new PetListResponseDto(pet.getId(), pet.getName(), pet.getImage()))
                .collect(Collectors.toList());
    }

    public List<PetListResponseDto> findPetListByNickname(String nickname) {
        User user = userRepository.findByNickname(nickname)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. nickname=" + nickname));

        List<Pet> petList = petRepository.findAllByUser(user);

        return petList.stream()
                .map(pet -> new PetListResponseDto(pet.getId(), pet.getName(), pet.getImage()))
                .collect(Collectors.toList());
    }

    public void updatePet(Long petId, PetRequestDto requestDto) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new IllegalArgumentException("해당 펫이 없습니다. id=" + petId));

        pet.update(requestDto);
    }

    public void deletePet(Long petId) {
        petRepository.deleteById(petId);
    }

    public Map<String,String> uploadUserImage(MultipartFile imageFile, Long petId) throws IOException {
        byte[] imageData = imageFile.getBytes();
        UUID uuid = UUID.randomUUID();
        String uploadDir = "pethub/src/main/upload/img/";
        String savedFileName = uuid.toString() + "_" + imageFile.getOriginalFilename();
        Path path = Paths.get(uploadDir,savedFileName);

        Files.write(path, imageData);

        Map<String,String> imagePath = new HashMap<>();
        imagePath.put("img","/upload_img/"+savedFileName);


        Pet pet = petRepository.findById(petId).get();
        pet.setImage(imagePath.get("img"));

        return imagePath;
    }
}