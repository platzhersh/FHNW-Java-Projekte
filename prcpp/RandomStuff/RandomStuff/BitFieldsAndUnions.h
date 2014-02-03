#pragma once

#include <iostream>
#include <stdint.h>

using namespace std;

void toId(struct CpuId *id, uint32_t value);
uint32_t fromId(struct CpuId *id);

// Bitfelder
struct CpuId {
	uint32_t Stepping			: 4;
	uint32_t Model				: 4;
	uint32_t FamilyID			: 4;
	uint32_t Type				: 2;
	uint32_t Reserved1			: 2;
	uint32_t ExtendedModel		: 4;
	uint32_t ExtendedFamilyID	: 8;
	uint32_t Reserved2			: 4;
};

union CPU {
	CpuId id;
	uint32_t num;
};

