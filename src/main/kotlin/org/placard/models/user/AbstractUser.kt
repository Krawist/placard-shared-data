package org.placard.models.user

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Inheritance
import jakarta.persistence.InheritanceType
import jakarta.persistence.OneToMany
import jakarta.persistence.Transient
import org.placard.models.Auditable
import org.placard.models.access.Accreditation
import org.placard.models.roles.Role

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
internal abstract class AbstractUser : Auditable() {
    @Transient
    @OneToMany(fetch = FetchType.EAGER)
    var roles: Collection<Role> = emptyList()

    @Transient
    @OneToMany(fetch = FetchType.EAGER)
    var accreditations: Collection<Accreditation> = emptyList()
}
