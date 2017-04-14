growth=[0.4049	0.55785	0.6208	0.6944	0.75135	0.7215	0.72845	0.78135	0.826684211	0.8284]
generation=[1 2 3 4 5 6 7 8 9 10]

figure;
plot(generation,growth,'-.r');
xlabel('Generations');
ylabel('Average Growth Rate')
title(' Evolving Growth Rate over Generations');
