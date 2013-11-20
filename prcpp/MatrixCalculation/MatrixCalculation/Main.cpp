#include "ch_fhnw_prcpp_Matrix.h"
#include <iostream>
using namespace std;

jdouble* multiply(jdouble* pa, jdouble* pb, jdouble* pr, jint ah, jint aw, jint bw) {

	// Multiply
	// in = row, il = column, field index = row*(width) + column
	double sum;
	for (int in = 0; in < ah; in++) {
		for (int il = 0; il < bw; il++) {
				
			sum = 0;
			for (int im = 0; im < aw; im++) {
				sum += *pa * *pb;
				pa++;		// +1 field in Array a
				pb += bw;	// +1 row in Array b

			}
			*pr = sum;
			pr++;			// +1 field in Array r
			pa -= aw;		// -1 row in Array a
			pb -= aw*bw;	// -1 matrix in Array b
			pb++;			// +1 field in Array b
		}				
		
		pa += aw;			// +1 row in Array a
		pb -= bw;			// -1 row in Array b
	}
	return pr;

}

jdouble* multiplyOld(jdouble* pa, jdouble* pb, jdouble* pr, jint ah, jint aw, jint bw) {

	// Multiply
	// i = row, j = column, field index = row + column
	double sum;
	for (int in = 0; in < ah; in++) {
		for (int il = 0; il < bw; il++) {
				
			sum = 0;
			for (int im = 0; im < aw; im++)
				sum += pa[in * aw + im] * pb[im * bw + il];
				//*(pr+iR) += *(pa+(in * aw + im)) * *(pb+(im * bw + il));
			//pr[in * bw + il] = sum;
			pr[in * bw + il] = sum;
		}				
	}
	return pr;

}


JNIEXPORT void JNICALL Java_ch_fhnw_prcpp_Matrix_multiplyC
	(JNIEnv * jnienv, jobject jo, jdoubleArray a, jdoubleArray b, jdoubleArray r, jint ah, jint aw, jint bw) {

		// Get Pointers to Arrays
		jboolean isCopy;
		jdouble *pa = jnienv->GetDoubleArrayElements(a,&isCopy);
		jdouble *pb = jnienv->GetDoubleArrayElements(b,&isCopy);
		jdouble *pr = jnienv->GetDoubleArrayElements(r,&isCopy);

		multiply(pa, pb, pr, ah, aw, bw);

		// Release Arrays, write back result
		jnienv->ReleaseDoubleArrayElements(a,pa,JNI_ABORT);
		jnienv->ReleaseDoubleArrayElements(b,pb,JNI_ABORT);
		jnienv->ReleaseDoubleArrayElements(r,pr,0);

}


JNIEXPORT void JNICALL Java_ch_fhnw_prcpp_Matrix_powerC
	(JNIEnv * jnienv, jobject jo, jdoubleArray a, jdoubleArray r, jint d, jint k) {

		// Get Pointers to Arrays
		jboolean isCopy;
		jdouble *pa = jnienv->GetDoubleArrayElements(a,&isCopy);
		jdouble *pr = jnienv->GetDoubleArrayElements(r,&isCopy);
		jdouble *pt = new jdouble[d*d];		// temporary result

			for (jint i=1; i < k; i++) {
				multiply(pr, pa, pt, d, d, d);

				int size = d*d;
				copy(pt,pt+size,pr);				

			}

		jnienv->ReleaseDoubleArrayElements(a,pa,JNI_ABORT);
		jnienv->ReleaseDoubleArrayElements(r,pr,0);
}