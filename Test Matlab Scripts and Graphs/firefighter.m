firefigter=[0 50 100 150 200 250 300 350 400 450 500 550 600 650 700 750 800 850 900 950 1000 ]
biomass= [.338 .336 .999 .999 .999 .999 .999 .999 .999 .999 .999 .999 .999 .999 .999 .999 .999 .999 .999 .999 .999]

figure;
plot(firefigter,biomass,'-.r*');
xlabel('Number of Fire-Fighters');
ylabel('Biomass')
title('Relationship Between Fire-Fighter and Biomass');

