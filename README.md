<p align="center">
  <img src="https://github.com/iamjosephmj/MVI-Clean-Rx/blob/main/images/mvi-intro.png" />
</p>

# MVI-Clean-Rx

MVI stands for Model - View - Intent. It is a relatively new but increasingly popular approach to
the whole Android Ecosystem. This shares ancestry with MVC just like MVP and MVVM


<p align="center">
  <img src="https://github.com/iamjosephmj/MVI-Clean-Rx/blob/main/images/mvi-inh.png" />
</p>

But MVP and MVVM are both a little more like MVC... MVP has <b>Presenter</b> class, MVVM has a <b>
ViewModel</b> class. Each of these classes take on similar responsibility like the <b>Controller</b>
of MVC, the only difference is in the way in which <b>Presenter</b>/<b>ViewModel</b> communicate to
the other layers of the architecture. In this Repo I had sketched out GitHubJobs app with MVI, all
the clean architecture layers are the same like we have in our previous repo, please refer
to <a href ="https://github.com/iamjosephmj/Rx-Clean">`Rx-Clean`</a> to get a better understanding
of the `GithubJobsApp`.

# Table Of Contents

* [Introduction](#Introduction)
* [Dependencies](#Dependencies)
* [The Concept](#The-Concept)
    * [Intents](#Intents)
    * [Looking Further into MVI](#Looking-Further-into-MVI)
    * [State And Immutable State](#State-And-Immutable-State)
    * [How to fit MVI in MVVM](#How-to-fit-MVI-in-MVVM)
* [Insights on Sealed classes](#Insights-on-Sealed-classes)
* [How does Rx fit in MVI](#How-does-Rx-fit-in-MVI)
* [MVI base interfaces](#MVI-base-interfaces)
* [Core Architecture Classes](#Core-Architecture-Classes)

## Introduction

MVI architecture introduces some new ideas that are not present on the other architectures that we
use now-a-days.

* <b>Unidirectional</b> Data Flow.
* <b>Immutable</b> State ( this is a lifesaver in most of the cases).
* User is Everywhere(user as a function :)).

Moving forward, we can see that the MVI architecture is a bit more involved than the other
architectures, But its unique nature helps us to simplify the flow of information and events in the
APP. In other words, it can lead to an architecture that's more comprehensible, maintainable and
bugFree :) approach.

MVI provides a systematic approach to adding features into our apps. Due to this benefits, many
Android Dev teams are now choosing MVI over MVP and MVVM. Buckle up! lets dive in...

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

Many of the advancements made in the software development in the past few years have come from the
web development world. Examples include tools and patterns
like <a href = "https://reactjs.org/">`React`</a>
and <a href="https://facebook.github.io/flux/">`Flux`</a>. These new tools tend to favour a
functional programming approach to software development and rethought the way in which you manage
the immutable state. They also use an event driven design, which fits in quite nicely with the UX of
mobile apps. MVI was created in the webWorld and is closely associated with the javascript framework
named <a href ="https://cycle.js.org/">`Cycle.js`</a> created
by <a href="https://staltz.com/">`Andre Staltz`</a>

<p align="center">
  <img src="https://github.com/iamjosephmj/MVI-Clean-Rx/blob/main/images/user.jpg" />
</p>

One of the key insights within Cycle.js and the MVI pattern is adding the user into the structure of
the software. Much of the code we write in web and mobile are heavily user focused, as opposed to
being processing-intensive. The code presents and interface that both accepts user inputs and shows
resulting output. But the place the user in architectures like MVP and MVVM are not entirely
transparent. In many ways, the user is kind of a view in those patterns. But in MVI, the user takes
a central role in terms of expressing their intents.

### Intents

In Android, the term Intent has a very specific meaning being a class in an android SDK. Intents are
what we, as Android developers, to bond different components in the Android system. We use them to
start activities and services, pass data to components like broadcast receivers. All Android
developers are well-versed in the use of the intent class for these and other purposes. However, the
world Intent in MVI is <b>totally unrelated</b> to the use of the class named Intent in in Android
SDK.Intent in MVI is more closely thought of as Intention(the intention of a user when interacting
with our app in some way).

<p align="center">
  <img src="https://github.com/iamjosephmj/MVI-Clean-Rx/blob/main/images/mvi_overview.png" />
</p>

Here is the high level flow of interaction between a user and typical modern software such a mobile
app. The app Starts up and the user begins interacting with it. We call the user interactions <b>
Intents</b> as in, the user intents for something to occur based on how they interact with the app.
For example, they might enter text or taps a button. These user intents are inputs into our
software, our software does something interesting with the intents in some type of model of our data
and then produces the output which is relayed back to the user. This flow of interaction can be
thought of as an event stream between the user and our software, with intents as input and turned
into results as outputs.

### Looking Further into MVI

<p align="center">
  <img src="https://github.com/iamjosephmj/MVI-Clean-Rx/blob/main/images/further.png" />
</p>

We interpret the user inputs as <b>Actions</b> that must be taken within our app. We translate
intents into actions to take on content and data within our app that us to our data repository which
may be network based or stored locally or both.

<p align="center">
  <img src="https://github.com/iamjosephmj/MVI-Clean-Rx/blob/main/images/processor.png" />
</p>

We use processors to perform actions on our data. The processors produce the results. And so, at
this point, the user <b>Intents</b> has been turned into an <b>Action</b> that has produced <b>
Results</b>. Now what's left is to get those results to the user. This is where the Idea of State
arises.

### State And Immutable State

State is the current status of all the information shown to the user. At a very high level in the
Android App, Example of the state includes
<b>Loading State</b>, <b>Empty State</b> and <b>Data State</b>. As the events occur in the app the
state is continuously updated based on the results of the events that have occurred. However, rather
than maintaining state immutable properties of objects in our system, in MVI a new state is
calculated for each event that occurs. The new state is based on both the previous state and the
results of the actions that have been taken by the user in interacting with the software. This
process of combining results with the previous state is called reduction in MVI. The new state is
passed along to the view of the app which renders the state for the display to the user. In this way
state is completely immutable. ie. It is never updated, but instead, it is just recalculated and
passed along to the view for rendering.

" ONE OF THE MAJOR SOURCES OF THE BUGS IN ANY KIND OF MOBILE DEVELOPMENT IS MUTABLE STATE " - Joseph James (that's
me :) )

So, by using Immutable state in this way in MVI, you have eliminated the source of many possible
bugs from your app development projects.

Looking at the whole picture, you can see that there is a unidirectional flow of data/events that
occur.

<b>User Events</b> -> <b>Actions</b> -> <b>Processors</b> -> <b>Results</b> -> <b>New State</b>
-> <b>User Events</b> -> <b>Actions</b>
and the loop continues.

### How to fit MVI in MVVM

- View wil have INTENTS reception and RENDER logic
- ViewModel will have :
    * Intent - Action Conversion
    * Action - Result Conversion
    * Result - State Reduction.

## Insights on Sealed classes

You can think of sealed classes as an advanced form of Enum, This let you define a limited hierarchy
of classes that can be used in kotlin when expressions in a similar way to enums. Unlike enums, you
can create multiple instances of sealed class sub type.

## How does Rx fit in MVI

We had discussed the theory behind MVI, from that you can imagine how streams of RxJava observables
fit naturally onto the unidirectional flow of MVI.

* User Intents gets packaged into Observable Streams
* This flow into actions
* interacts with repo
* then flows into Result
* then reduced to State.

You can think of the unidirectional flow as a stream of observables going round and round as the
user interacts with you app ( moment of truth guys :) ).

Now we are all set to start.

## MVI base interfaces

<p align="center">
  <img src="https://github.com/iamjosephmj/MVI-Clean-Rx/blob/main/images/mvi-base.png" />
</p>

The flow of events around the MVI cycle consists of intents, actions, results and state. We want to
be able to send these type of entities around using Rx Observables and will create base interfaces
to represent each type.

The specific items will implement the corresponding interfaces in order to be tagged as that type.

For instance, We will use `MVIIntent` Interface in the above diagram to represent all user intents.

<p align="center">
  <img src="https://github.com/iamjosephmj/MVI-Clean-Rx/blob/main/images/viewmodel-counterpart.png" />
</p>

We also need the ViewModel to process the intents and provide states that the views can observe, the
View needs to provide intents to the ViewModel and be able to render new states. from the viewModel.
So, lets also create interfaces for ViewModel and Views.

* <a href ="https://github.com/iamjosephmj/MVI-Clean-Rx/tree/main/presentation/src/main/java/io/iamjosephmj/presentation/mvi/mvibase/MVIAction.kt">`MVIAction`</a>
* <a href ="https://github.com/iamjosephmj/MVI-Clean-Rx/tree/main/presentation/src/main/java/io/iamjosephmj/presentation/mvi/mvibase/MVIIntent.kt">`MVIIntent`</a>
* <a href ="https://github.com/iamjosephmj/MVI-Clean-Rx/tree/main/presentation/src/main/java/io/iamjosephmj/presentation/mvi/mvibase/MVIResult.kt">`MVIResult`</a>
* <a href ="https://github.com/iamjosephmj/MVI-Clean-Rx/tree/main/presentation/src/main/java/io/iamjosephmj/presentation/mvi/mvibase/MVIView.kt">`MVIView`</a>
* <a href ="https://github.com/iamjosephmj/MVI-Clean-Rx/tree/main/presentation/src/main/java/io/iamjosephmj/presentation/mvi/mvibase/MVIViewModel.kt">`MVIViewModel`</a>
* <a href ="https://github.com/iamjosephmj/MVI-Clean-Rx/tree/main/presentation/src/main/java/io/iamjosephmj/presentation/mvi/mvibase/MVIViewState.kt">`MVIViewState`</a>

## Core Architecture Classes

Please refer to this classes for more understanding.

* <a href ="https://github.com/iamjosephmj/MVI-Clean-Rx/tree/main/presentation/src/main/java/io/iamjosephmj/presentation/mvi/intent/GitHubLoadJobsIntent.kt">`GitHubLoadJobsIntent`</a>
* <a href ="https://github.com/iamjosephmj/MVI-Clean-Rx/tree/main/presentation/src/main/java/io/iamjosephmj/presentation/mvi/action/GithubLoadJobsAction.kt">`GithubLoadJobsAction`</a>
* <a href ="https://github.com/iamjosephmj/MVI-Clean-Rx/tree/main/presentation/src/main/java/io/iamjosephmj/presentation/mvi/processor/GitHubJobsProcessorHolder.kt">`GitHubJobsProcessorHolder`</a>
* <a href ="https://github.com/iamjosephmj/MVI-Clean-Rx/tree/main/presentation/src/main/java/io/iamjosephmj/presentation/mvi/results/GitHubJobsResult.kt">`GitHubJobsResult`</a>
* <a href ="https://github.com/iamjosephmj/MVI-Clean-Rx/tree/main/presentation/src/main/java/io/iamjosephmj/presentation/mvi/viewstate/GitHubJobsViewState.kt">`GitHubJobsViewState`</a>
* <a href ="https://github.com/iamjosephmj/MVI-Clean-Rx/tree/main/app/src/main/java/io/iamjosephmj/mvi_rx_clean/ui/viewmodels/JobsViewModel.kt">`JobsViewModel`</a>
* <a href ="https://github.com/iamjosephmj/MVI-Clean-Rx/tree/main/app/src/main/java/io/iamjosephmj/mvi_rx_clean/ui/screens/JobsActivity.kt">`JobsActivity`</a>

## Contributing to MVI-Clean-Rx

Feel free to raise a PR and link in an issue if you think I had went wrong in any parts of the MVI(all suggestions accepted).

