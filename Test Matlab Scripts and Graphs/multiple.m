single=[0.27145	0.289	0.29775	0.3067	0.31135	0.3044	0.30845	0.31525	0.321157895	0.31515]
multipleS=[0.3102	0.33015	0.3313	0.33245	0.3245	0.3306	0.3206	0.33455	0.33595	0.3279]
generation=[1 2 3 4 5 6 7 8 9 10]

a1=plot(generation,single,'-b*');
M1 = 'Single Species';
hold on
a2=plot(generation,multipleS,'-.rS');
M2 = 'Multiple Species';
xlabel('Generation','FontSize',16);
ylabel('Average Biomass','FontSize',16);
legend([a1;a2], M1, M2);
title('Comparison between Single Species and Multiple Species ','FontSize',16)