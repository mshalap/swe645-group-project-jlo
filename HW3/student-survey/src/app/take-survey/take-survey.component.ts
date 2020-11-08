import { Component, OnInit } from '@angular/core';
import { Survey } from '../survey'
import { ApiService } from '../api.service';

@Component({
  selector: 'app-take-survey',
  templateUrl: './take-survey.component.html',
  styleUrls: ['./take-survey.component.css']
})
export class TakeSurveyComponent implements OnInit {

  title = 'Take Survey';
  surveys:Survey[];

  model = new Survey('','','','','','','','',null,'','','','','','','','','','');

  constructor(private apiService:ApiService) {}

  ngOnInit() { }

  submitted = false;
  message = ""

  onSubmit() { 
    this.submitted = true; 
    this.addSurvey();
  }
 
  addSurvey() {
    this.apiService.addSurvey(this.model)
      .subscribe(
      succeed=>{
        this.message = "Thank you for submitting the form!";
      },
      error=>
      {
        this.message = "Form submission failed!";
      } )      
  }

}
