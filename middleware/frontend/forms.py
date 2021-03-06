from django.forms import ModelForm
from django import forms
from django.core import validators
from django.contrib.auth.models import User
from middleware.core import models
from django.contrib.auth.forms import UserCreationForm

class GroupForm(ModelForm):
    class Meta:
        model = models.Group
        exclude = ('students',)

class AssistantForm(ModelForm):
    class Meta:
        model = models.Assistant
        exclude = ('group','external_id',)
        
class CourseForm(ModelForm):
    class Meta:
        model = models.Course
        exclude = ('students',)

class RegisterForm(UserCreationForm):
    email = forms.EmailField()
    first_name = forms.CharField()
    last_name = forms.CharField()

class UpdateGradeForm(forms.Form):
    new_grade = forms.IntegerField()

class SearchForm(forms.Form):
    query = forms.CharField(max_length=100, label="Search for student")
