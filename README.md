
<p align="center">
  <img src="https://github.com/iamjosephmj/MVI-Clean-Rx/blob/main/images/mvi-intro.png" />
</p>


# MVI-Clean-Rx (Work In Progress)

MVI stands for Model - View - Intent. It is a relatively new but increasingly popular approach to the whole Android Ecosystem.
This shares ancestry with MVC just like MVP and MVVM


<p align="center">
  <img src="https://github.com/iamjosephmj/MVI-Clean-Rx/blob/main/images/mvi-inh.png" />
</p>

But MVP and MVVM are both a little more like MVC... MVP has <b>Presenter</b> class, MVVM has a <b>ViewModel</b> class. Each of these classes take on similar 
responsibility like the <b>Controller</b> of MVC, the only difference is in the way in which <b>Presenter</b>/<b>ViewModel</b> communicate to 
the other layers of the architecture. In this Repo I had sketched out GitHubJobs app with MVI, all the clean architecture layers are the 
same like we have in our previous repo, please refer to <a href ="https://github.com/iamjosephmj/Rx-Clean">`Rx-Clean`</a> to get a 
better understanding of the `GithubJobsApp`.

# Table Of Contents

* [Introduction](#Introduction)
* [Dependencies](#Dependencies)
* [The Concept](#The-Concept)

## Introduction

MVI architecture introduces some new ideas that are not present on the other architectures that we use now-a-days. 

* <b>Unidirectional</b> Data Flow.
* <b>Immutable</b> State ( this is a lifesaver in most of the cases).
* User is Everywhere(user as a function :)).

Moving forward, we can see that the MVI architecture is a bit more involved than the other architectures, But its unique nature 
helps us to simplify the flow of information and events in the APP. In other words, it can lead to an architecture that's more comprehensible, maintainable 
and bugFree :) approach.

MVI provides a systematic approach to adding features into our apps. Due to this benefits, many Android Dev teams are now choosing MVI over MVP and 
MVVM. Buckle up! lets dive in...

## Dependencies

* <a href="https://dagger.dev/">`Dagger2`</a>
* <a href="https://github.com/sockeqwe/AdapterDelegates">`AdapterDelegates`</a>
* <a href="https://github.com/ReactiveX/RxKotlin">`RxKotlin`</a>
* <a href="https://developer.android.com/topic/libraries/architecture/viewmodel">`ViewModels`</a>
* <a href="https://github.com/square/moshi">`Moshi`</a>
* <a href="https://github.com/airbnb/lottie-android">`lottie-android`</a>
* <a href="https://github.com/willowtreeapps/spruce-android">`spruce-android`</a>
* <a href="https://github.com/bumptech/glide">`Glide`</a>

## The Concept

Many of the advancements made in the software development in the past few years have come from the web development world. 
Examples include tools and patterns like <a href = "https://reactjs.org/">`React`</a> and <a href="https://facebook.github.io/flux/">`Flux`</a>. These 
new tools tend to favour a functional programming approach to software development and rethought the way in which you manage the immutable state. 
They also use an event driven design, which fits in quite nicely with the UX of mobile apps. MVI was created in the webWorld and is closely associated 
with the javascript framework named <a href ="https://cycle.js.org/">`Cycle.js`</a> created by <a href="https://staltz.com/">`Andre Staltz`</a>

<p align="center">
  <img src="https://github.com/iamjosephmj/MVI-Clean-Rx/blob/main/images/user.jpg" />
</p>

On of the key insights within Cycle.js and the MVI pattern is adding the user into the structure of the software. 