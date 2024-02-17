package org.placard.service.form

import io.micronaut.data.model.Page
import io.micronaut.http.HttpResponse
import org.placard.models.form.Form
import org.placard.remote.FormDto

internal interface FormService {

    fun create(formDto: FormDto) : HttpResponse<Form>

    fun update(formDto: FormDto) : HttpResponse<Form>

    fun search() : HttpResponse<Page<Form>>

}