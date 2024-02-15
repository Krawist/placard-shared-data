package org.placard.models.user

import io.micronaut.data.annotation.DateCreated
import io.micronaut.data.annotation.DateUpdated
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.Inheritance
import jakarta.persistence.InheritanceType
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Transient
import org.placard.models.access.Accreditation
import org.placard.models.roles.Role
import java.time.Instant

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
internal abstract class AbstractUser {
    @Id
    var identifier: String = ""

    @Transient
    @OneToMany(fetch = FetchType.EAGER)
    var roles: Collection<Role> = emptyList()

    @Transient
    @OneToMany(fetch = FetchType.EAGER)
    var accreditations: Collection<Accreditation> = emptyList()

    @ManyToOne
    var createdBy: User? = null

    @ManyToOne
    var lastUpdateBy: User? = null

    @DateCreated
    var createdAt: Instant? = null

    @DateUpdated
    var lastUpdateAt: Instant? = null
}
