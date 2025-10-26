package com.example.proyecto_pi3_backend.Feedback.domain;

import com.example.proyecto_pi3_backend.Feedback.dto.FeedbackRequestDTO;
import com.example.proyecto_pi3_backend.Feedback.dto.FeedbackResponseDTO;
import com.example.proyecto_pi3_backend.Feedback.infrastructure.FeedbackRepository;
import com.example.proyecto_pi3_backend.MenuItems.domain.MenuItems;
import com.example.proyecto_pi3_backend.MenuItems.infrastructure.MenuItemsRepository;
import com.example.proyecto_pi3_backend.Orders.domain.Orders;
import com.example.proyecto_pi3_backend.Orders.infrastructure.OrdersRepository;
import com.example.proyecto_pi3_backend.User.domain.Users;
import com.example.proyecto_pi3_backend.User.infrastructure.UserRepository;
import com.example.proyecto_pi3_backend.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final UserRepository userRepository;
    private final OrdersRepository ordersRepository;
    private final MenuItemsRepository menuItemsRepository;


    @Transactional
    public FeedbackResponseDTO createFeedback(FeedbackRequestDTO feedbackRequestDTO) {
        Users user = userRepository.findById(feedbackRequestDTO.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + feedbackRequestDTO.getUserId()));

        Feedback feedback = new Feedback();
        feedback.setRating(feedbackRequestDTO.getRating());
        feedback.setComment(feedbackRequestDTO.getComment());
        feedback.setUser(user);

        if (feedbackRequestDTO.getOrderId() != null) {
            Orders order = ordersRepository.findById(feedbackRequestDTO.getOrderId())
                    .orElseThrow(() -> new ResourceNotFoundException("Orden no encontrada con ID: " + feedbackRequestDTO.getOrderId()));
            feedback.setOrder(order);
        }

        if (feedbackRequestDTO.getMenuItemId() != null) {
            MenuItems menuItem = menuItemsRepository.findById(feedbackRequestDTO.getMenuItemId())
                    .orElseThrow(() -> new ResourceNotFoundException("Menu item no encontrado con ID: " + feedbackRequestDTO.getMenuItemId()));
            feedback.setMenuItem(menuItem);
        }
        
        feedback = feedbackRepository.save(feedback);
        
        return new FeedbackResponseDTO(
                feedback.getId(),
                feedback.getRating(),
                feedback.getComment(),
                user.getFirstName() + " " + user.getLastName(),
                feedback.getMenuItem() != null ? feedback.getMenuItem().getItemName() : "N/A"
        );
    }

    public List<FeedbackResponseDTO> getFeedbackByMenuItem(Long menuItemId) {
        MenuItems menuItem = menuItemsRepository.findById(menuItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Menu item no encontrado con ID: " + menuItemId));
        
        List<Feedback> feedbacks = feedbackRepository.findAll().stream()
                .filter(fb -> fb.getMenuItem() != null && fb.getMenuItem().getId().equals(menuItemId))
                .collect(Collectors.toList());
        
        return feedbacks.stream()
                .map(fb -> new FeedbackResponseDTO(
                        fb.getId(),
                        fb.getRating(),
                        fb.getComment(),
                        fb.getUser().getFirstName() + " " + fb.getUser().getLastName(),
                        menuItem.getItemName()
                ))
                .collect(Collectors.toList());
    }

    public List<FeedbackResponseDTO> getFeedbackByUser(Long userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + userId));
        
        List<Feedback> feedbacks = feedbackRepository.findAll().stream()
                .filter(fb -> fb.getUser().getId().equals(userId))
                .collect(Collectors.toList());
        
        return feedbacks.stream()
                .map(fb -> new FeedbackResponseDTO(
                        fb.getId(),
                        fb.getRating(),
                        fb.getComment(),
                        user.getFirstName() + " " + user.getLastName(),
                        fb.getMenuItem() != null ? fb.getMenuItem().getItemName() : "N/A"
                ))
                .collect(Collectors.toList());
    }

    public List<FeedbackResponseDTO> getAllFeedbacks() {
        List<Feedback> feedbacks = feedbackRepository.findAll();
        
        return feedbacks.stream()
                .map(fb -> new FeedbackResponseDTO(
                        fb.getId(),
                        fb.getRating(),
                        fb.getComment(),
                        fb.getUser().getFirstName() + " " + fb.getUser().getLastName(),
                        fb.getMenuItem() != null ? fb.getMenuItem().getItemName() : "N/A"
                ))
                .collect(Collectors.toList());
    }
}
