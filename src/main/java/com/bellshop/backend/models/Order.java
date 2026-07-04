package com.bellshop.backend.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_name", nullable = false, length = 100)
    private String clientName;

    @Column(name = "client_phone", nullable = false, length = 50)
    private String clientPhone;

    @Column(name = "delivery_address", columnDefinition = "TEXT")
    private String deliveryAddress;

    // On stocke l'enum sous forme de chaîne de caractères (VARCHAR) en BDD
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private OrderStatus status = OrderStatus.NOUVELLE;

    @Column(name = "is_custom", nullable = false)
    private boolean isCustom = false;

    @Column(name = "custom_description", columnDefinition = "TEXT")
    private String customDescription;

    // Relation Many-To-One : Plusieurs commandes peuvent concerner le même produit
    // JoinColumn indique la clé étrangère product_id (nullable = true car sur-mesure pur possible)
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = true)
    private Product product;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public Order() {
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    // --- GETTERS ET SETTERS ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getClientName() { return clientName; }
    public void setClientName(String clientName) { this.clientName = clientName; }

    public String getClientPhone() { return clientPhone; }
    public void setClientPhone(String clientPhone) { this.clientPhone = clientPhone; }

    public String getDeliveryAddress() { return deliveryAddress; }
    public void setDeliveryAddress(String deliveryAddress) { this.deliveryAddress = deliveryAddress; }

    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }

    public boolean isCustom() { return isCustom; }
    public void setCustom(boolean custom) { isCustom = custom; }

    public String getCustomDescription() { return customDescription; }
    public void setCustomDescription(String customDescription) { this.customDescription = customDescription; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public LocalDateTime getCreatedAt() { return createdAt; }
}