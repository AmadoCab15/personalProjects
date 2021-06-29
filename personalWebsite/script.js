'use strcit'


var isItOpen = false;

/* --------------------------Left and Right Project Arrows -----------------*/

document.querySelector('.fa-angle-double-right').addEventListener('click', function () {
    var currProject = document.querySelector('.projectPicture');
    
    if (currProject.src.includes('ants.jpg')) {
        currProject.src = "img/game.jpg";
        $('.ants').removeClass('currBar');
        $('.game').addClass('currBar');
        changeToGame();
    } else if (currProject.src.includes('game.jpg')) {
        currProject.src = 'img/maps.jpg';
        $('.game').removeClass('currBar');
        $('.maps').addClass('currBar');
        changeToMaps();
    } else if (currProject.src.includes('maps.jpg')) {
        currProject.src = 'img/cats.jpg';
        $('.maps').removeClass('currBar');
        $('.cats').addClass('currBar');
        changeToCats();
    } else {
        currProject.src = 'img/ants.jpg'; 
        $('.ants').addClass('currBar');
        $('.cats').removeClass('currBar');
        changeToAnts();
    }
});


document.querySelector('.fa-angle-double-left').addEventListener('click', function () {
    var currProject = document.querySelector('.projectPicture');
    
    if (currProject.src.includes('game.jpg')) {
        currProject.src = "img/ants.jpg";
        $('.game').removeClass('currBar');
        $('.ants').addClass('currBar');
        changeToAnts();
    } else if (currProject.src.includes('maps.jpg')) {
        currProject.src = 'img/game.jpg';
        $('.maps').removeClass('currBar');
        $('.game').addClass('currBar');
        changeToGame();
    } else if (currProject.src.includes('cats.jpg')) {
        currProject.src = 'img/maps.jpg';
        $('.cats').removeClass('currBar');
        $('.maps').addClass('currBar');
        changeToMaps();
    } else {
        currProject.src = 'img/cats.jpg';
        $('.ants').removeClass('currBar');
        $('.cats').addClass('currBar');
        changeToCats();
    }
});



/* --------------------------Leadership & Extracurriculars ----------------*/


/* -------HES--------*/

document.querySelector('.hesPic').addEventListener('click', function () {
    
    var status = document.querySelector('.leadershipPop');
    var title = document.querySelector('.titlePop');
    var year = document.querySelector('.yearPop');
    var desc = document.querySelector('.descPop');
    var website = document.querySelector('.urlPop');
    
    document.querySelector('.leadershipPop').style.top = $(window).scrollTop() + 100;
    
    title.innerHTML = 'Hispanic Engineers & Scientists';
    year.innerHTML = 'Fall 2019 – Present';
    desc.innerHTML = "Engaged in activities with other STEM major students and attended monthly meetings with CEO's from tech companies.";
    website.innerHTML = 'https://hes.berkeley.edu/';
    website.href = 'https://hes.berkeley.edu/';
    
    if (status.className.includes('active')) {
        $('.leadershipPop').removeClass('active');
        $('.overlay').addClass('hiddenOver');
    } else {
        $('.leadershipPop').addClass('active');
        $('.overlay').removeClass('hiddenOver');
    }  
});


/* -------HU--------*/

document.querySelector('.huPic').addEventListener('click', function () {
    
    var status = document.querySelector('.leadershipPop');
    var title = document.querySelector('.titlePop');
    var year = document.querySelector('.yearPop');
    var desc = document.querySelector('.descPop');
    
    document.querySelector('.leadershipPop').style.top = $(window).scrollTop() + 100;
    
    var website = document.querySelector('.urlPop');
    title.innerHTML = 'Hermanos Unidos';
    year.innerHTML = 'Fall 2019 – Present';
    desc.innerHTML = "With the help of other LatinX members, I offer and deliver food to those who need the most.";
    website.innerHTML = 'https://sites.google.com/view/hermanosundiosdeucb/home';
    website.href = 'https://sites.google.com/view/hermanosundiosdeucb/home';
    
    if (status.className.includes('active')) {
        $('.leadershipPop').removeClass('active');
        $('.overlay').addClass('hiddenOver');
    } else {
        $('.leadershipPop').addClass('active');
        $('.overlay').removeClass('hiddenOver');
    }
});

/* -------Mixed at Berkeley--------*/


document.querySelector('.mixPic').addEventListener('click', function () {
    var status = document.querySelector('.leadershipPop');
    var title = document.querySelector('.titlePop');
    var year = document.querySelector('.yearPop');
    var desc = document.querySelector('.descPop');
    var website = document.querySelector('.urlPop');
    
    document.querySelector('.leadershipPop').style.top = $(window).scrollTop() + 100;
    
    title.innerHTML = 'Mixed @ Berkeley';
    year.innerHTML = 'Semester of Fall 2019 ';
    desc.innerHTML = "Networked and supported other mixed-identifying students of color on navigating college. Helped inpire high school students to pursue a college career.";
    website.innerHTML = 'http://www.mixedatberkeley.com/';
    website.href = 'http://www.mixedatberkeley.com/';
    
    if (status.className.includes('active')) {
        $('.leadershipPop').removeClass('active');
        $('.overlay').addClass('hiddenOver');
    } else {
        $('.leadershipPop').addClass('active');
        $('.overlay').removeClass('hiddenOver');
    }


});

document.querySelector('.closingTab').addEventListener('click', function () {
    var status = document.querySelector('.leadershipPop');
    
    if (status.className.includes('active')) {
        $('.leadershipPop').removeClass('active');
        $('.overlay').addClass('hiddenOver');
    } else {
        $('.leadershipPop').addClass('active');
        $('.overlay').removeClass('hiddenOver');
    }

});


document.querySelector('.overlay').addEventListener('click', function () {
    isItOpen = !isItOpen;
    var overlayIshidden = document.querySelector('.overlay').className.includes('hiddenOver');
    var openBox = document.querySelector('.leadershipPop').className.includes('active');
    if (!isItOpen) {
        isItOpen = false;
        $('.leadershipPop').removeClass('active');
        $('.overlay').addClass('hiddenOver');
    } 

});


/*--------------------------------------------------Helper Functions------------------------------------------------------*/


function changeToAnts() {
    var titleBox = document.querySelector('.projectTitle');
    var descBox = document.querySelector('.projectDescription');
    titleBox.innerHTML = "Ants vs. Some Bees";
    descBox.innerHTML = "2D game in Python that imitates 'Plants vs. Zombies' by PopCap Games. The main goal is to stop the bees invasion from entering to the base. The project requires experience in OOP and navigating external libraries.";
    
}

function changeToGame() {
    var titleBox = document.querySelector('.projectTitle');
    var descBox = document.querySelector('.projectDescription');
    titleBox.innerHTML  = "On my way!";
    descBox.innerHTML = "2D game in Java with the main goal of delivering pizza without being interrupted by “pizza monsters”. The proceses of this work involves using data strucutres like binary trees, hashing tables, and linked lists.";
    
}

function changeToMaps() {
    var titleBox = document.querySelector('.projectTitle');
    var descBox = document.querySelector('.projectDescription');
    titleBox.innerHTML  = "BearMaps";
    descBox.innerHTML = "Web mapping application for people that lives in the city of Berkeley, California. Included features are search bars with autocompletion, calculations of optimal routes between two endpoints, and map rastering to zoom in.";
    
}

function changeToCats() {
    var titleBox = document.querySelector('.projectTitle');
    var descBox = document.querySelector('.projectDescription');
    titleBox.innerHTML  = " Autocorrect Typing Software";
    descBox.innerHTML = "Program that measures typing speed and autocorrects as it reads the input. Developing the project requires understanding in recurssion, input-reading, and instant feedback for the user.";
}


